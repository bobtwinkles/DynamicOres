package tk.sirtwinkles.dynores.worldgen;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;
import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class DeepDimTeleporter extends Teleporter {

    public DeepDimTeleporter(WorldServer worldServer) {
        super(worldServer);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
        // TODO Auto-generated method stub
        log.info("Asked to place an entity in a portal <3");
        super.placeInPortal(par1Entity, par2, par4, par6, par8);
    }

    @Override
    public boolean makePortal(Entity par1Entity) {
        log.info("Asked to make a poral <3");
        // TODO Auto-generated method stub
        return super.makePortal(par1Entity);
    }
}
