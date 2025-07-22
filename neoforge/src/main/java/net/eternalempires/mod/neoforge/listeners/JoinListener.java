package net.eternalempires.mod.neoforge.listeners;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.util.CommonService;
import net.eternalempires.mod.neoforge.ClientModEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Slf4j
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class JoinListener {

    @SubscribeEvent
    public static void onPlayerLogin(final @NotNull ClientPlayerNetworkEvent.LoggingIn event) {
        final ServerData serverData = Minecraft.getInstance().getCurrentServer();

        if (serverData == null) {
            return;
        }

        final String address = serverData.ip;

        log.debug("Joined server: {}", address);

        CommonService commonService = Objects.requireNonNull(
                ClientModEvents.getCommonService(),
                "ClientModEvents is not initialized!"
        );

        commonService.handleLastServer(address);
    }
}
