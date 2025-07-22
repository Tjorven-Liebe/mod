package net.eternalempires.mod.forge.listeners;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.util.CommonService;
import net.eternalempires.mod.forge.ClientModEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Slf4j
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class JoinListener {

    @SubscribeEvent
    public static void onPlayerLogin(final @NotNull ClientPlayerNetworkEvent.LoggingIn event) {
        final ServerData serverData = Minecraft.getInstance().getCurrentServer();

        if (serverData == null) {
            return;
        }

        final String address = serverData.ip;

        log.debug("Joined server: {}", address);

        final CommonService commonService = Objects.requireNonNull(
                ClientModEvents.getCommonService(),
                "ClientModEvents is not initialized!"
        );

        commonService.handleLastServer(address);
    }
}
