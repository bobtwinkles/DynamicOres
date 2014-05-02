package tk.sirtwinkles.dynores.renderer;

import java.awt.Image;
import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class MaskAtlasSprite extends TextureAtlasSprite {

    public int[] data;

    public MaskAtlasSprite(String name, int[] data, int width, int height) {
        super(name);
        this.data = data;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location) {
        BufferedImage bl = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bl.setRGB(0, 0, width, height, data, 0, width);
        BufferedImage[] images = new BufferedImage[1 + Minecraft.getMinecraft().gameSettings.mipmapLevels];
        images[0] = bl;
        for (int i = 0; i < images.length - 1; ++i) {
            int scaledWidth = bl.getWidth() / 2;
            int scaledHeight = bl.getHeight() / 2;
            Image scaled = bl.getScaledInstance(scaledWidth, scaledHeight, BufferedImage.SCALE_DEFAULT);
            // images[i + 1] = new BufferedImage(scaledWidth, scaledHeight,
            // BufferedImage.TYPE_INT_ARGB);
            // Graphics2D gfx = images[i + 1].createGraphics();
            // gfx.drawImage(scaled, 0, 0, null);
            // gfx.dispose();
        }
        super.loadSprite(images, null, true);
        return false;
    }
}
