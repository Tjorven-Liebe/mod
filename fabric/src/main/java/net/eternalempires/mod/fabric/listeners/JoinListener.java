package net.eternalempires.mod.fabric.listeners;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.util.CommonService;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import org.jetbrains.annotations.NotNull;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class JoinListener implements ClientPlayConnectionEvents.Join {

    private final CommonService commonService;

    @Override
    public void onPlayReady(
            final @NotNull ClientPacketListener clientPacketListener,
            final @NotNull PacketSender packetSender,
            final @NotNull Minecraft minecraft
    ) {
        final ServerData serverData = Minecraft.getInstance().getCurrentServer();

        if (serverData == null) {
            return;
        }

        final String ip = serverData.ip;

        log.debug("Joined server: {}", ip);

        this.commonService.handleLastServer(ip);
    }
}
