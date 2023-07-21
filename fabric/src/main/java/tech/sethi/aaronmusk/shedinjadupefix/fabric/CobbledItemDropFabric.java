package tech.sethi.aaronmusk.shedinjadupefix.fabric;

import net.fabricmc.api.ModInitializer;
import tech.sethi.aaronmusk.shedinjadupefix.ShedinjaDupeFix;

public class CobbledItemDropFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ShedinjaDupeFix.init();
    }
}