package tk.sirtwinkles.dynores.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import tk.sirtwinkles.dynores.ClientProxy;
import tk.sirtwinkles.dynores.DynamicOresMod;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameData;

public class OreRenderer implements ISimpleBlockRenderingHandler {

    public static final OreRenderer RENDERER = new OreRenderer();

    private static int rendererId = RenderingRegistry.getNextAvailableRenderId();

    public void init() {
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        ClientProxy proxy = (ClientProxy) DynamicOresMod.proxy;
        String blockName = Block.blockRegistry.getNameForObject(block);
        if (blockName.startsWith("minecraft"))
            blockName = blockName.substring(10);
        IIcon blockIcon = proxy.terrain.getAtlasSprite(blockName);
        this.renderInventoryBlock(block, metadata, modelId, renderer, blockIcon);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        ClientProxy proxy = (ClientProxy) DynamicOresMod.proxy;
        String blockName = Block.blockRegistry.getNameForObject(block);
        if (blockName.startsWith("minecraft"))
            blockName = blockName.substring(10);
        IIcon blockIcon = proxy.terrain.getAtlasSprite("mask_" + blockName);
        int yo = -1;
        Block below = world.getBlock(x, y + yo, z);
        while (below.getRenderType() == this.rendererId) {
            --yo;
            below = world.getBlock(x, y + yo, z);
        }
        if (proxy.whiteList.contains(GameData.getBlockRegistry().getNameForObject(below))) {
            renderer.renderStandardBlock(world.getBlock(x, y + yo, z), x, y, z);
        } else {
            IIcon stone = proxy.terrain.getAtlasSprite("stone");
            renderer.setOverrideBlockTexture(stone);
            renderer.renderStandardBlock(block, x, y, z);
        }
        renderer.setOverrideBlockTexture(blockIcon);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.clearOverrideBlockTexture();
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return rendererId;
    }

    private void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer, IIcon override) {
        block.setBlockBoundsForItemRender();
        Tessellator t = Tessellator.instance;
        renderer.setRenderBoundsFromBlock(block);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        t.startDrawingQuads();
        t.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, override);
        t.draw();
        t.startDrawingQuads();
        t.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, override);
        t.draw();
        t.startDrawingQuads();
        t.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, override);
        t.draw();
        t.startDrawingQuads();
        t.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, override);
        t.draw();
        t.startDrawingQuads();
        t.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, override);
        t.draw();
        t.startDrawingQuads();
        t.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, override);
        t.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }
}
