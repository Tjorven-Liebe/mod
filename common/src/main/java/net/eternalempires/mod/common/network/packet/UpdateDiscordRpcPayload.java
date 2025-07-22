package net.eternalempires.mod.common.network.packet;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.network.AbstractEternalEmpiresPayload;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Slf4j
public final class UpdateDiscordRpcPayload extends AbstractEternalEmpiresPayload {

    @NotNull
    public static final StreamCodec<@NotNull ByteBuf, @NotNull UpdateDiscordRpcPayload> BYTEBUF_CODEC =
            StreamCodec.of((buf, value) -> buf.writeBytes(value.data),
                    buf -> {
                        final byte[] data = new byte[buf.readableBytes()];

                        buf.readBytes(data);

                        return new UpdateDiscordRpcPayload(data);
                    });

    @NotNull
    public static final StreamCodec<@NotNull RegistryFriendlyByteBuf, @NotNull UpdateDiscordRpcPayload> FORGE_CODEC =
            StreamCodec.of(
                    (buf, packet) -> buf.writeBytes(packet.data),
                    buf -> {
                        final byte[] data = new byte[buf.readableBytes()];

                        buf.readBytes(data);

                        return new UpdateDiscordRpcPayload(data);
                    }
            );

    @NotNull
    public static final StreamCodec<@NotNull FriendlyByteBuf, @NotNull UpdateDiscordRpcPayload> FABRIC_CODEC =
            StreamCodec.of((buf, value) -> buf.writeBytes(value.data),
                    buf -> {
                        final byte[] data = new byte[buf.readableBytes()];

                        buf.readBytes(data);

                        return new UpdateDiscordRpcPayload(data);
                    });

    public UpdateDiscordRpcPayload(final @NotNull FriendlyByteBuf buffer) {
        super(buffer);
    }

    public UpdateDiscordRpcPayload(final byte[] data) {
        super(data);
    }

    @Override
    public @NotNull Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }

    @Nullable
    private String extractRegionName() {
        return extractJsonField("data.name");
    }

    @Override
    public void handlePayload(final @NotNull RichPresenceService service) {
        log.debug("Received JSON: {}", json);

        final String type = getTypeField();

        if (!"player_enter_region".equalsIgnoreCase(type)) {
            log.debug("Ignoring non-region payload: type={}", type);
            return;
        }

        final String regionName = extractRegionName();

        if (regionName != null) {
            log.debug("Updating location: {}", regionName);

            service.updateLocation(regionName);
        } else {
            log.debug("Failed to extract region name from JSON");
        }
    }
}