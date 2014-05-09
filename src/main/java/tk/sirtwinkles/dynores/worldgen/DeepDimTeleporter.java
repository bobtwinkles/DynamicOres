package tk.sirtwinkles.dynores.worldgen;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;
import net.minecraft.entity.Entity;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class DeepDimTeleporter extends Teleporter {
    
    private LongHashMap positionCache;

    public DeepDimTeleporter(WorldServer worldServer) {
        super(worldServer);
        positionCache = new LongHashMap();
    }

    @Override
    public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
        if (par1Entity.dimension == 0) {
            //travelToOverworldPortal(entity);
        } else {
            //travelToPortal()
        }
    }
    
    @Override
    public void removeStalePortalLocations(long par1) {
    }
}
