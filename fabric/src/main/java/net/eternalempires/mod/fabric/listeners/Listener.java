package net.eternalempires.mod.fabric.listeners;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.client.DiscordRPCManager;
import net.eternalempires.mod.fabric.client.EternalEmpiresClientFabric;

@Slf4j
public class Listener {

    protected void handleLastServer(final String address) {
        if (address.equals(EternalEmpiresClientFabric.getLastServerAddress())) {
            log.info("Bungee switch detected. Keeping Discord RPC running.");

            return;
        }

        if (!Constants.SERVER_IPS.contains(address)) {
            return;
        }

        log.info("IP matched! Starting Discord RPC.");

        DiscordRPCManager.start();
    }
}
