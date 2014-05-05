package tk.sirtwinkles.dynores.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class DimensionalElevator extends TileEntity {

    @Override
    public boolean canUpdate() {
        return true;
    }
    
    @Override
    public void updateEntity() {
    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        // TODO Auto-generated method stub
        return super.getRenderBoundingBox();
    }
}
