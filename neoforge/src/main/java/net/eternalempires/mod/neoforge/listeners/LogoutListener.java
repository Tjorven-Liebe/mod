package net.eternalempires.mod.neoforge.listeners;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.util.NetworkService;
import net.eternalempires.mod.common.util.ServerCheckService;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.eternalempires.mod.neoforge.ClientModEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@Slf4j
public class LogoutListener {

    @SubscribeEvent
    public static void onPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        final ServerCheckService serverCheckService = ClientModEvents.getServerCheckService();

        final NetworkService networkService = serverCheckService.getNetworkService();
        final String lastServerAddress = networkService.getLastServerAddress();
        final RichPresenceService richPresenceService = serverCheckService.getRichPresenceService();

        // If IP is known and not a Bungee switch
        if (lastServerAddress == null || !richPresenceService.isStarted()) {
            return;
        }

        log.info("Disconnected from server: {}. Stopping Discord RPC.", lastServerAddress);

        richPresenceService.stop();
        networkService.setLastServerAddress(null);
    }

}
