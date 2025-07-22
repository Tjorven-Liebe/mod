package net.eternalempires.mod.common.util;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import org.jetbrains.annotations.NotNull;

@Slf4j
@Getter
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ServerCheckService {

    private final RichPresenceService richPresenceService;
    private final NetworkService networkService;

    public void handleLastServer(final @NotNull String address) {
        if (address.equals(this.networkService.getLastServerAddress())) {
            log.info("Bungee switch detected. Keeping Discord RPC running.");

            return;
        }

        if (!Constants.SERVER_IPS.contains(address)) {
            return;
        }

        log.info("IP matched! Starting Discord RPC.");

        this.richPresenceService.start();
        this.networkService.setLastServerAddress(address);
    }
}
