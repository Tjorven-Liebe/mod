package net.eternalempires.mod.fabric.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.fabric.client.EternalEmpiresClientFabric;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;

@Slf4j
@RequiredArgsConstructor
public class JoinListener extends Listener implements ClientPlayConnectionEvents.Join {

    @Override
    public void onPlayReady(ClientPacketListener clientPacketListener, PacketSender packetSender, Minecraft minecraft) {
        final ServerData serverData = Minecraft.getInstance().getCurrentServer();

        if (serverData == null) {
            return;
        }

        final String ip = serverData.ip;

        log.debug("Joined server: {}", ip);

        this.handleLastServer(ip);

        EternalEmpiresClientFabric.setLastServerAddress(ip);
    }
}
