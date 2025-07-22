package net.eternalempires.mod.fabric.client;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.client.EternalEmpiresClient;
import net.eternalempires.mod.fabric.listeners.DisconnectListener;
import net.eternalempires.mod.fabric.listeners.JoinListener;
import net.eternalempires.mod.fabric.network.PacketHandlersFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import org.jetbrains.annotations.Nullable;

@Slf4j
public class EternalEmpiresClientFabric implements ClientModInitializer {

    @Setter
    @Getter
    @Nullable
    private static String lastServerAddress = null;

    @Override
    public void onInitializeClient() {
        EternalEmpiresClient.init();
        PacketHandlersFabric.register();

        ClientPlayConnectionEvents.JOIN.register(new JoinListener());
        ClientPlayConnectionEvents.DISCONNECT.register(new DisconnectListener());
    }

}