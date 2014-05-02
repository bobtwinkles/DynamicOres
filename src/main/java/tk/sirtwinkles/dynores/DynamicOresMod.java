package tk.sirtwinkles.dynores;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DynamicOresMod.MODID, version = DynamicOresMod.VERSION)
public class DynamicOresMod {
    public static final String MODID = "DynOres";
    public static final String VERSION = "1.0";

    // Runtime data
    @SidedProxy(modId = DynamicOresMod.MODID, clientSide = "tk.sirtwinkles.dynores.ClientProxy", serverSide = "tk.sirtwinkles.dynores.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}
