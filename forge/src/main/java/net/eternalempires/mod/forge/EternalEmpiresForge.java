package net.eternalempires.mod.forge;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.EternalEmpires;
import net.minecraftforge.fml.common.Mod;

@Slf4j
@Mod(Constants.MOD_ID)
public class EternalEmpiresForge {

    public EternalEmpiresForge() {
        EternalEmpires.init();
    }
}