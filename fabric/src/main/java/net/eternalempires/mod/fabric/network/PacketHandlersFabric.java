package net.eternalempires.mod.fabric.network;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.network.packet.UpdateDiscordRpcPayload;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PacketHandlersFabric {

    private final RichPresenceService richPresenceService;

    public void register() {
        PayloadTypeRegistry.playS2C().register(UpdateDiscordRpcPayload.TYPE, UpdateDiscordRpcPayload.FABRIC_CODEC);

        ClientPlayNetworking.registerGlobalReceiver(UpdateDiscordRpcPayload.TYPE, this::handleContext);
    }

    private void handleContext(final UpdateDiscordRpcPayload payload, final ClientPlayNetworking.Context context) {
        // TODO - Tjorven: should you ignore the closeable?
        context.client().execute(() -> {
            try {
                log.debug("Received payload: {}", payload.toString());

                // TODO - Tjorven: better handling for payload | not inside of a packet
                payload.handlePayload(richPresenceService);
            } catch (Exception e) {
                log.error("Failed to handle payload [{}]", payload.getClass().getName(), e);
            }
        });
    }
}