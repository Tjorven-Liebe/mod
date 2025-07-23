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

package net.eternalempires.mod.common;

import lombok.experimental.UtilityClass;
import net.minecraft.SharedConstants;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This class is used to store some common constants.
 *
 * @author EternalEmpires
 * @since 08/06/2025
 */
@UtilityClass
public final class Constants {

    public static final @NotNull String MOD_ID = "eternalempires";
    public static final @NotNull String DISCORD_APPLICATION_ID = "1191464487191056404";

    public static final @NotNull List<@NotNull String> SERVER_IPS = List.of(
            "beta.eternalempires.dev"
    );

    public static final @NotNull String MINECRAFT_VERSION = SharedConstants.getCurrentVersion().name();

    public static final @NotNull String RICH_PRESENCE_STATE = "Playing on Eternal Empires";

    public static final @NotNull String BIG_IMAGE_KEY = "eternalempires_e_1400x1400";
    public static final @NotNull String BIG_IMAGE_TEXT = "EternalEmpires.net";

    public static final @NotNull String SMALL_IMAGE_KEY = "grass_block";
    public static final @NotNull String SMALL_IMAGE_TEXT = String.format("Minecraft %s", MINECRAFT_VERSION);
}