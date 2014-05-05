package tk.sirtwinkles.dynores.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ElevatorRenderer implements ISimpleBlockRenderingHandler {
    public static ElevatorRenderer RENDERER = new ElevatorRenderer();
    
    public final int rendererId;
    
    private ElevatorRenderer() {
        rendererId = RenderingRegistry.getNextAvailableRenderId();
        Models.ELEVATOR_RAILS.initData();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        //noop b/c shouldRender3DInInventory returns false
    }
    
    private void renderSubBlock(RenderBlocks renderer, Block block, int x, int y, int z, int xo, int yo, int zo, int w, int h, int d) {
        final double pixelSize = 1.0 / 16.0;
        double basex = xo * pixelSize;
        double basey = yo * pixelSize;
        double basez = zo * pixelSize;
        renderer.setRenderBounds(basex, basey, basez, basex + w * pixelSize, basey + h * pixelSize, basez + d * pixelSize);
        renderer.renderStandardBlock(block, x, y, z);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.draw();
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);
        Models.ELEVATOR_RAILS.model.renderAll();
        GL11.glPopMatrix();
        tessellator.startDrawingQuads();
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return this.rendererId;
    }
}
