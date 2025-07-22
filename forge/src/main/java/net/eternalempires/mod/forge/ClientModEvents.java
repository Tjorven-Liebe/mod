package net.eternalempires.mod.forge;

import com.google.inject.Injector;
import lombok.Getter;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.client.EternalEmpiresClient;
import net.eternalempires.mod.common.util.CommonService;
import net.eternalempires.mod.forge.network.PacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @Getter
    @Nullable
    private static CommonService commonService;

    @SubscribeEvent
    public static void clientSetup(final @NotNull FMLClientSetupEvent event) {
        final Injector injector = EternalEmpiresClient.init();

        commonService = injector.getInstance(CommonService.class);

        event.enqueueWork(PacketHandler::register);
    }
}