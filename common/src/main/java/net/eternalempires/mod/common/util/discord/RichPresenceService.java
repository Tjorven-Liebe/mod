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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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