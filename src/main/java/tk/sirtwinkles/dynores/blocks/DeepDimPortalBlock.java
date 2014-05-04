package tk.sirtwinkles.dynores.blocks;

import tk.sirtwinkles.dynores.DynamicOresMod;
import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class DeepDimPortalBlock extends Block {

    public DeepDimPortalBlock() {
        super(Material.rock);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity collidee) {
        if (collidee.dimension != DynamicOresMod.dimId && collidee.timeUntilPortal <= 0 && !world.isRemote) {
            collidee.travelToDimension(DynamicOresMod.dimId);
            collidee.timeUntilPortal = 10;
        } else {
            collidee.travelToDimension(0);
            collidee.timeUntilPortal = 10;
        }
    }
}
