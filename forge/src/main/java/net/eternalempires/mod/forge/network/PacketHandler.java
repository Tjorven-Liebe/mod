package net.eternalempires.mod.forge.network;

import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.network.UpdateDiscordRpcPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

public class PacketHandler {
    private static final SimpleChannel UPDATE_RPC = ChannelBuilder.named(
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "mod"))
            .serverAcceptedVersions((status, i) -> true)
            .clientAcceptedVersions((status, i) -> true)
            .networkProtocolVersion(1)
            .simpleChannel()
            .play()
            .clientbound()
            .add(UpdateDiscordRpcPayload.class, UpdateDiscordRpcPayload.FORGE_CODEC, (packet, context) -> {
                packet.handlePayload();
            })
            .build();

    public static void register() {
        // method is called in Forge Main class to register UPDATE_RPC above
    }
}