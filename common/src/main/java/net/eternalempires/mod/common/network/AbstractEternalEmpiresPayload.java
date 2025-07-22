package net.eternalempires.mod.common.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Strictness;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.network.packet.UpdateDiscordRpcPayload;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public abstract class AbstractEternalEmpiresPayload implements CustomPacketPayload {

    @NotNull
    public static final CustomPacketPayload.Type<@NotNull UpdateDiscordRpcPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "mod"));

    protected final byte[] data;

    @Nullable
    protected final String json;

    public AbstractEternalEmpiresPayload(final @NotNull FriendlyByteBuf buffer) {
        this.data = new byte[buffer.readableBytes()];
        this.json = new String(this.data, StandardCharsets.UTF_8);

        buffer.readBytes(this.data);
    }

    public AbstractEternalEmpiresPayload(byte[] data) {
        this.data = data;
        this.json = new String(data, StandardCharsets.UTF_8);
    }

    public void encode(final @NotNull FriendlyByteBuf buffer) {
        buffer.writeBytes(data);
    }

    public byte[] data() {
        return data;
    }

    @Nullable
    public String json() {
        return json;
    }

    @Nullable
    public String getTypeField() {
        return extractJsonField("type");
    }

    @Nullable
    protected String extractJsonField(final @NotNull String fieldName) {
        if (this.json == null) return null;

        final int jsonStart = this.json.indexOf('{');
        if (jsonStart == -1) return null;

        final String rawJson = this.json.substring(jsonStart);

        final JsonElement root;
        try {
            final JsonReader reader = new JsonReader(new StringReader(rawJson));
            reader.setStrictness(Strictness.LENIENT);
            root = JsonParser.parseReader(reader);
        } catch (JsonSyntaxException e) {
            log.error("Failed to parse JSON field '{}': {}", fieldName, e.getMessage());
            return null;
        }

        final String[] parts = fieldName.split("\\.");
        JsonElement current = root;

        for (String part : parts) {
            if (!current.isJsonObject()) return null;
            current = current.getAsJsonObject().get(part);
            if (current == null) return null;
        }

        return current.isJsonPrimitive() ? current.getAsString() : current.toString();
    }

    public abstract void handlePayload(@NotNull RichPresenceService service);
}
