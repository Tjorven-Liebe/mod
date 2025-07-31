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

package net.eternalempires.mod.fabric.listeners;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.util.CommonService;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to detect if the user joins a server
 *
 * @author EternalEmpires
 * @since 07/23/2025
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class JoinListener implements ClientPlayConnectionEvents.Join {

    private final CommonService commonService;

    /**
     * This method gets triggered by the event handler in {@link net.eternalempires.mod.fabric.client.EternalEmpiresClientModInitializer}.
     * This will call the update method in the commonService.
     *
     * @param clientPacketListener the packetlistener
     * @param packetSender the packetsender
     * @param minecraft the minecraft instance
     */
    @Override
    public void onPlayReady(
            final @NotNull ClientPacketListener clientPacketListener,
            final @NotNull PacketSender packetSender,
            final @NotNull Minecraft minecraft
    ) {
        final ServerData serverData = Minecraft.getInstance().getCurrentServer();

        if (serverData == null) {
            return;
        }

        final String ip = serverData.ip;

        log.debug("Joined server: {}", ip);

        this.commonService.handleLastServer(ip);
    }
}
