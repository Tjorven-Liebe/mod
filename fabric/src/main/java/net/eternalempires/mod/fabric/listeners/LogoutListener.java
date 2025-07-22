package net.eternalempires.mod.fabric.listeners;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.util.NetworkService;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LogoutListener implements ClientPlayConnectionEvents.Disconnect {

    private final NetworkService networkService;
    private final RichPresenceService richPresenceService;

    @Override
    public void onPlayDisconnect(ClientPacketListener clientPacketListener, Minecraft minecraft) {
        final String lastServerIP = this.networkService.getLastServerAddress();

        if (lastServerIP == null || !this.richPresenceService.isStarted()) {
            return;
        }

        log.info("Disconnected from server: {}. Stopping Discord RPC.", lastServerIP);

        this.richPresenceService.stop();

        this.networkService.setLastServerAddress(null);
    }
}