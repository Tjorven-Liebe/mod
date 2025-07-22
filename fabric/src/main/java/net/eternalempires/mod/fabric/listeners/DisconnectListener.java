package net.eternalempires.mod.fabric.listeners;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.client.DiscordRPCManager;
import net.eternalempires.mod.fabric.client.EternalEmpiresClientFabric;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

@Slf4j
public class DisconnectListener extends Listener implements ClientPlayConnectionEvents.Disconnect {

    @Override
    public void onPlayDisconnect(ClientPacketListener clientPacketListener, Minecraft minecraft) {
        final String lastServerIP = EternalEmpiresClientFabric.getLastServerAddress();

        if (lastServerIP == null || !DiscordRPCManager.isStarted()) {
            return;
        }

        log.debug("Disconnected from server: {}. Stopping Discord RPC.", lastServerIP);

        DiscordRPCManager.stop();

        EternalEmpiresClientFabric.setLastServerAddress(null);
    }
}