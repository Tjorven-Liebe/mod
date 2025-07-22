package net.eternalempires.mod.fabric.client;

import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.client.EternalEmpiresClient;
import net.eternalempires.mod.fabric.listeners.JoinListener;
import net.eternalempires.mod.fabric.listeners.LogoutListener;
import net.eternalempires.mod.fabric.network.PacketHandlersFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

@Slf4j
public final class EternalEmpiresClientModInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        final Injector injector = EternalEmpiresClient.init();

        injector.getInstance(PacketHandlersFabric.class).register();

        ClientPlayConnectionEvents.JOIN.register(injector.getInstance(JoinListener.class));
        ClientPlayConnectionEvents.DISCONNECT.register(injector.getInstance(LogoutListener.class));
    }
}