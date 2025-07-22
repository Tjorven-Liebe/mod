/*
 * MIT License
 *
 * Copyright (c) 2025 EternalEmpires.net
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.eternalempires.mod.neoforge.listeners;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.util.CommonService;
import net.eternalempires.mod.common.util.NetworkService;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import net.eternalempires.mod.neoforge.ClientModEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This class is used to detect if the client has left a server.
 *
 * @author EternalEmpires
 * @since 07.23. 2025
 */
@Slf4j
public final class LogoutListener {

    /**
     * This method gets triggered by the forge event executor.<br/>
     * This will reset the lastserver in the networkService and stops the discord rich-presence.
     *
     * @param event container of event-related information
     */
    @SubscribeEvent
    public static void onPlayerLogout(final @NotNull ClientPlayerNetworkEvent.LoggingOut event) {
        final CommonService commonService = Objects.requireNonNull(
                ClientModEvents.getCommonService(),
                "ClientModEvents is not initialized!"
        );

        final NetworkService networkService = commonService.getNetworkService();
        final String lastServerAddress = networkService.getLastServerAddress();
        final RichPresenceService richPresenceService = commonService.getRichPresenceService();

        // If IP is known and not a Bungee switch
        if (lastServerAddress == null || !richPresenceService.isStarted()) {
            return;
        }

        log.debug("Disconnected from server: {}. Stopping Discord RPC.", lastServerAddress);

        richPresenceService.stop();

        networkService.setLastServerAddress(null);
    }
}
