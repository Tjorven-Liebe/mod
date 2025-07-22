package net.eternalempires.mod.fabric.client;

import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.client.EternalEmpiresClient;
import net.eternalempires.mod.fabric.listeners.DisconnectListener;
import net.eternalempires.mod.fabric.listeners.JoinListener;
import net.eternalempires.mod.fabric.network.PacketHandlersFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

@Slf4j
public class EternalEmpiresClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Injector injector = EternalEmpiresClient.init();
        PacketHandlersFabric.register();

        ClientPlayConnectionEvents.JOIN.register(new JoinListener());
        ClientPlayConnectionEvents.DISCONNECT.register(new DisconnectListener());
    }

}