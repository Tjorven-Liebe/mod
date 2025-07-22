package net.eternalempires.mod.fabric;

import net.eternalempires.mod.common.EternalEmpires;
import net.fabricmc.api.ModInitializer;

public final class EternalEmpiresFabricModInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        EternalEmpires.init();
    }
}
