package tk.sirtwinkles.dynores;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import tk.sirtwinkles.dynores.blocks.BlockWalker;
import tk.sirtwinkles.dynores.blocks.DeepDimPortalBlock;
import tk.sirtwinkles.dynores.blocks.RegisterBlock;
import tk.sirtwinkles.dynores.worldgen.DynOresDeepDimProvider;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;
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

    public static DynOresDeepDimProvider provider;
    public static IWorldGenerator deepDimGenerator;
    
    @RegisterBlock(unlocalizedName = "dynores.portal", name="dynores_portal", textureName="dynores_portal")
    public static Block portal = new DeepDimPortalBlock();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	Configuration c = new Configuration(new File(Loader.instance().getConfigDir(), "DynamicOres.cfg"));
    	
    	BlockWalker.walkClass(getClass(), c);
    	
    	c.save();
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}
