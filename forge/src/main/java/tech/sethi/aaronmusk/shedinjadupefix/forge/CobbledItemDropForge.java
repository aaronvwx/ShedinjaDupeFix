package tech.sethi.aaronmusk.shedinjadupefix.forge;

import dev.architectury.platform.forge.EventBuses;
import tech.sethi.aaronmusk.shedinjadupefix.ShedinjaDupeFix;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ShedinjaDupeFix.MOD_ID)
public class CobbledItemDropForge {
    public CobbledItemDropForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(ShedinjaDupeFix.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ShedinjaDupeFix.init();
    }
}