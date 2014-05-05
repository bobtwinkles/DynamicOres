package tk.sirtwinkles.dynores.blocks;

import tk.sirtwinkles.dynores.renderer.ElevatorRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ElevatorRail extends Block {
    public ElevatorRail() {
        super(Material.rock);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
