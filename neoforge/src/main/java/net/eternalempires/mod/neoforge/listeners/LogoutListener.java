package net.eternalempires.mod.neoforge.listeners;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.util.CommonService;
import net.eternalempires.mod.common.util.NetworkService;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.eternalempires.mod.neoforge.ClientModEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Slf4j
public final class LogoutListener {

    @SubscribeEvent
    public static void onPlayerLogout(final @NotNull ClientPlayerNetworkEvent.LoggingOut event) {
        final CommonService commonService = Objects.requireNonNull(
                ClientModEvents.getCommonService(),
                "ClientModEvents is not initialized!"
        );

        final NetworkService networkService = commonService.getNetworkService();
        final String lastServerAddress = networkService.getLastServerAddress();
        final RichPresenceService richPresenceService = commonService.getRichPresenceService();

        // If IP is known and not a Bungee switch
        if (lastServerAddress == null || !richPresenceService.isStarted()) {
            return;
        }

        log.debug("Disconnected from server: {}. Stopping Discord RPC.", lastServerAddress);

        richPresenceService.stop();

        networkService.setLastServerAddress(null);
    }
}
