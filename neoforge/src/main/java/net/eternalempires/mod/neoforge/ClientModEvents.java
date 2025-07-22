package net.eternalempires.mod.neoforge;

import com.google.inject.Injector;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.client.EternalEmpiresClient;
import net.eternalempires.mod.common.network.packet.UpdateDiscordRpcPayload;
import net.eternalempires.mod.common.util.CommonService;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Slf4j
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientModEvents {

    @Getter
    private static CommonService commonService;

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        final Injector injector = EternalEmpiresClient.init();

        commonService = injector.getInstance(CommonService.class);
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Constants.MOD_ID)
                .versioned("1")
                .optional();
        registrar.playToClient(
                UpdateDiscordRpcPayload.TYPE,
                UpdateDiscordRpcPayload.BYTEBUF_CODEC,
                (updateDiscordRpcPayload, context) -> context.enqueueWork(() -> {
                    log.debug("Received JSON: {}", updateDiscordRpcPayload.json());

                    updateDiscordRpcPayload.handlePayload(commonService.getRichPresenceService());
                })
        );
    }
}
