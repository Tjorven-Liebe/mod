/*
 * MIT License
 *
 * Copyright (c) 2025 EternalEmpires.net
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.eternalempires.mod.common.util.discord;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.eternalempires.mod.common.Constants;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This service is used to handle rich-presence data and to send it to the discord-client.
 * The lib "drpc" by artikia is used.
 *
 * @author EternalEmpires
 * @since 07/22/2025
 */
@Slf4j
@Singleton
public final class RichPresenceService implements Runnable {

    @NotNull
    private final Thread mainThread = Thread.currentThread();

    @NotNull
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
            .setNameFormat("Discord-RPC-Callback-Thread").build()
    );

    @Getter
    private boolean started = false;

    @Nullable
    private Thread callbackThread;

    private long startTimeStamp;

    /**
     * This will start the rich-presence for a client.
     */
    public void start() {
        if (started) {
            return;
        }

        started = true;

        startTimeStamp = System.currentTimeMillis();

        final DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
                .setReadyEventHandler((user) -> {
                    log.debug("Discord Rich-Presence ready for user: {}", user.username);
                })
                .build();

        DiscordRPC.discordInitialize(Constants.DISCORD_APPLICATION_ID, handlers, true);

        final DiscordRichPresence presence = new DiscordRichPresence.Builder("Playing on Eternal Empires")
                //.setDetails("")
                //.setBigImage("icon", "Eternal Adventure")
                .setBigImage("eternalempires_e_1400x1400", "EternalEmpires.net")
                .setSmallImage("grass_block", "Minecraft " + Constants.VERSION)  //new line for small image
                .setStartTimestamps(startTimeStamp)
                .build();

        DiscordRPC.discordUpdatePresence(presence);

        executorService.execute(this);
    }

    /**
     * This method is for internal use. However, it will keep the rich-presence updated.
     *
     * @apiNote Internal use
     */
    @ApiStatus.Internal
    @Override
    public void run() {
        callbackThread = Thread.currentThread();

        Preconditions.checkState(
                callbackThread != mainThread,
                "This method should not be called from the main thread!"
        );

        while (!callbackThread.isInterrupted()) {
            DiscordRPC.discordRunCallbacks();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("Discord Callback Thread interrupted", e);

                break;
            }
        }
    }

    /**
     * This method is used to stop the rich-presence.
     */
    public void stop() {
        if (!started) {
            return;
        }

        started = false;

        if (callbackThread != null && callbackThread.isAlive()) {
            callbackThread.interrupt();
        }

        DiscordRPC.discordShutdown();

        log.debug("Discord Rich-Presence stopped.");
    }

    /**
     * This method is used to update the location of player.
     *
     * @param location most likely the region where the player is in.
     */
    public void updateLocation(final @NotNull String location) {
        if (!started) {
            return;
        }

        final DiscordRichPresence presence = new DiscordRichPresence.Builder(location)
                .setDetails("Playing on Eternal Empires")
                //.setBigImage("icon", "Wanderer")
                .setBigImage("eternalempires_e_1400x1400", "EternalEmpires.net")
                .setSmallImage("grassblock", "Minecraft " + Constants.VERSION)  //new line, for small image
                .setStartTimestamps(startTimeStamp)
                .build();

        DiscordRPC.discordUpdatePresence(presence);
    }
}