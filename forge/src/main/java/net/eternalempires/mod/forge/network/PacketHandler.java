package net.eternalempires.mod.forge.network;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.network.packet.UpdateDiscordRpcPayload;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class PacketHandler {

    @NotNull
    private final RichPresenceService richPresenceService;

    @NotNull
    private final SimpleChannel updateRichPresence = ChannelBuilder.named(
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "mod"))
            .serverAcceptedVersions((status, i) -> true)
            .clientAcceptedVersions((status, i) -> true)
            .networkProtocolVersion(1)
            .simpleChannel()
            .play()
            .clientbound()
            .add(UpdateDiscordRpcPayload.class, UpdateDiscordRpcPayload.FORGE_CODEC,
                    (packet, context) -> {
                        packet.handlePayload(this.richPresenceService);
                    })
            .build();

    public static void register() {
        // method is called in Forge Main class to register UPDATE_RPC above
    }
}