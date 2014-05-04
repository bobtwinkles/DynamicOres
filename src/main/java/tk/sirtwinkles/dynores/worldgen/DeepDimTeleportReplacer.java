package tk.sirtwinkles.dynores.worldgen;

import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import tk.sirtwinkles.dynores.DynamicOresMod;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DeepDimTeleportReplacer {

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (event.world instanceof WorldServer) {
            WorldServer server = (WorldServer) event.world;
            if (DimensionManager.getWorld(DynamicOresMod.dimId).equals(server)) {
                Teleporter teleporter = new DeepDimTeleporter(server);
                ObfuscationReflectionHelper.setPrivateValue(WorldServer.class, server, teleporter, "worldTeleporter");
            }
        }
    }
}
