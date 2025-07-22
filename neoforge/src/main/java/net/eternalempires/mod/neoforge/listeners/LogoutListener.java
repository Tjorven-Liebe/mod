package net.eternalempires.mod.neoforge.listeners;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.util.CommonService;
import net.eternalempires.mod.common.util.NetworkService;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.eternalempires.mod.neoforge.ClientModEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@Slf4j
public class LogoutListener {

    @SubscribeEvent
    public static void onPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        final CommonService commonService = ClientModEvents.getCommonService();

        final NetworkService networkService = commonService.getNetworkService();
        final String lastServerAddress = networkService.getLastServerAddress();
        final RichPresenceService richPresenceService = commonService.getRichPresenceService();

        // If IP is known and not a Bungee switch
        if (lastServerAddress == null || !richPresenceService.isStarted()) {
            return;
        }

        log.info("Disconnected from server: {}. Stopping Discord RPC.", lastServerAddress);

        richPresenceService.stop();

        networkService.setLastServerAddress(null);
    }
}
