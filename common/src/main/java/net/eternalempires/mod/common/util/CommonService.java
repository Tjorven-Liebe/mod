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

package net.eternalempires.mod.common.util;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.util.discord.RichPresenceService;
import org.jetbrains.annotations.NotNull;

/**
 * This service combines some of the dependencies for easy access in mod-loaders where DI is not that easy. ( Forge ;( )
 *
 * @author EternalEmpires
 * @since 07/22/2025
 */
@Slf4j
@Getter
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CommonService {

    @NotNull
    private final RichPresenceService richPresenceService;

    @NotNull
    private final NetworkService networkService;

    /**
     * this will set the address of the last server the player was on
     *
     * @param address the address
     */
    public void handleLastServer(final @NotNull String address) {
        if (address.equals(this.networkService.getLastServerAddress())) {
            log.debug("Server switch detected. Keeping Discord RPC running.");

            return;
        }

        if (!Constants.SERVER_IPS.contains(address)) {
            return;
        }

        log.debug("IP matched! Starting Discord Rich-Presence.");

        this.richPresenceService.start();
        this.networkService.setLastServerAddress(address);
    }
}
