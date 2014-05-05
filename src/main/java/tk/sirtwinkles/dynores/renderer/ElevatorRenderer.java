package tk.sirtwinkles.dynores.renderer;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ElevatorRenderer extends TileEntitySpecialRenderer {
    public static ElevatorRenderer RENDERER = new ElevatorRenderer();
    public static int burnedRenderId;
    
    private ElevatorRenderer() {
        Models.ELEVATOR_RAILS.initData();
        burnedRenderId = RenderingRegistry.getNextAvailableRenderId(); //Burn a render ID so things work
    }

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8) {
        // TODO Auto-generated method stub
        Models.ModelLocation ml = Models.ELEVATOR_RAILS;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ml.texture);
        ml.model.renderAll();
        GL11.glPopMatrix();
    }

}
