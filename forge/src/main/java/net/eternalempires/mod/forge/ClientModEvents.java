package net.eternalempires.mod.forge;

import com.google.inject.Injector;
import lombok.Getter;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.client.EternalEmpiresClient;
import net.eternalempires.mod.common.util.ServerCheckService;
import net.eternalempires.mod.forge.network.PacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @Getter
    private static ServerCheckService serverCheckService;

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        Injector injector = EternalEmpiresClient.init();

        serverCheckService = injector.getInstance(ServerCheckService.class);

        event.enqueueWork(PacketHandler::register);
    }
}