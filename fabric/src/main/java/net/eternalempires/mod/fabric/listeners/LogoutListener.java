package net.eternalempires.mod.fabric.listeners;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.util.NetworkService;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.jetbrains.annotations.NotNull;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class LogoutListener implements ClientPlayConnectionEvents.Disconnect {

    @NotNull
    private final NetworkService networkService;

    @NotNull
    private final RichPresenceService richPresenceService;

    @Override
    public void onPlayDisconnect(
            final @NotNull ClientPacketListener clientPacketListener,
            final @NotNull Minecraft minecraft
    ) {
        final String lastServerIP = this.networkService.getLastServerAddress();

        if (lastServerIP == null || !this.richPresenceService.isStarted()) {
            return;
        }

        log.debug("Disconnected from server: {}. Stopping Discord RPC.", lastServerIP);

        this.richPresenceService.stop();

        this.networkService.setLastServerAddress(null);
    }
}