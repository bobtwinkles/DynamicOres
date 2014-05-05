package tk.sirtwinkles.dynores.blocks;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tk.sirtwinkles.dynores.renderer.ElevatorRenderer;
import tk.sirtwinkles.dynores.tileentity.DimensionalElevator;

public class ElevatorCore extends Block implements ITileEntityProvider {

    public ElevatorCore() {
        super(Material.rock);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return ElevatorRenderer.burnedRenderId;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        log.info("Created tile entity");
        return new DimensionalElevator();
    }
}
