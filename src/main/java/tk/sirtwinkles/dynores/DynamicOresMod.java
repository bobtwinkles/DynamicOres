package tk.sirtwinkles.dynores;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.Teleporter;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import tk.sirtwinkles.dynores.blocks.BlockWalker;
import tk.sirtwinkles.dynores.blocks.DeepDimPortalBlock;
import tk.sirtwinkles.dynores.blocks.ElevatorRail;
import tk.sirtwinkles.dynores.blocks.RegisterBlock;
import tk.sirtwinkles.dynores.worldgen.DeepDimProvider;
import tk.sirtwinkles.dynores.worldgen.DeepDimTeleportReplacer;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = DynamicOresMod.MODID, version = DynamicOresMod.VERSION, name = "Dynamic Ores")
public class DynamicOresMod {
    public static final String MODID = "dynores";
    public static final String VERSION = "1.0";

    // Runtime data
    @SidedProxy(modId = DynamicOresMod.MODID, clientSide = "tk.sirtwinkles.dynores.ClientProxy", serverSide = "tk.sirtwinkles.dynores.ServerProxy")
    public static CommonProxy proxy;

    public static int providerId;
    public static int dimId;

    public static CreativeTabs dynoresCreativeTab;

    public static Teleporter teleporter;

    // Blocks
    @RegisterBlock(unlocalizedName = "dynores.portal", name = "dynores_portal", textureName = "dynores_portal")
    public static Block portal = new DeepDimPortalBlock();
    
    @RegisterBlock(unlocalizedName = "dynores.elevator.rail", name = "dynores_elevator_rail", textureName = "dynores_elevator_rail")
    public static Block elevatorRail = new ElevatorRail();

    // -- end blocks --

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new DeepDimTeleportReplacer());
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Configuration c = new Configuration(new File(Loader.instance().getConfigDir(), "DynamicOres.cfg"));

        Property dimId = c.get("dimmension", "dimId", DimensionManager.getNextFreeDimId());
        dimId.comment = "The dimmension id of our dimmension";
        Property providerId = c.get("dimmension", "providerId", dimId.getInt());
        providerId.comment = "The ID of our world provider. Do not change unless you know what you are doing";

        dynoresCreativeTab = new DynamicOresCreativeTab();

        this.dimId = dimId.getInt();
        this.providerId = providerId.getInt();

        DimensionManager.registerProviderType(this.providerId, DeepDimProvider.class, false);
        DimensionManager.registerDimension(this.dimId, this.providerId);

        BlockWalker.walkClass(getClass(), c);

        c.save();

        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        DimensionManager.initDimension(this.dimId);
    }
}
