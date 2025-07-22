package net.eternalempires.mod.forge.listeners;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.util.CommonService;
import net.eternalempires.mod.common.util.NetworkService;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.eternalempires.mod.forge.ClientModEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Slf4j
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class LogoutListener {

    @SubscribeEvent
    public static void onPlayerLogout(final @NotNull ClientPlayerNetworkEvent.LoggingOut event) {
        final CommonService commonService = Objects.requireNonNull(
                ClientModEvents.getCommonService(),
                "ClientMod is not initialized!"
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
