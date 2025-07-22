package net.eternalempires.mod.neoforge;

import lombok.extern.slf4j.Slf4j;
import net.eternalempires.mod.common.Constants;
import net.eternalempires.mod.common.EternalEmpires;
import net.neoforged.fml.common.Mod;

@Slf4j
@Mod(Constants.MOD_ID)
public class EternalEmpiresNeoForge {

    public EternalEmpiresNeoForge() {
        EternalEmpires.init();
    }
}