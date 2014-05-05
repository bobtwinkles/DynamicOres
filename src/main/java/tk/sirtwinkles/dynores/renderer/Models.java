package tk.sirtwinkles.dynores.renderer;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;

import java.io.IOException;

import com.google.common.base.Throwables;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import tk.sirtwinkles.dynores.DynamicOresMod;

public class Models {
    private static final String modid = DynamicOresMod.MODID.toLowerCase();
    private static final String modelBase = "models/";
    private static final String textureBase = "textures/models/";
    
    public static final ModelLocation ELEVATOR_RAILS = new ModelLocation("elevator_rails");
    
    public static class ModelLocation {
        public ResourceLocation textureLocation;
        public ResourceLocation modelLocation;
        public IModelCustom model;
        public int texture;
        
        public ModelLocation(String baseName) {
            textureLocation = new ResourceLocation(modid, textureBase + baseName + ".png");
            modelLocation = new ResourceLocation(modid, modelBase + baseName + ".obj");
        }
        
        public void initData() {
            model = AdvancedModelLoader.loadModel(modelLocation);
            SimpleTexture loadTexture = new SimpleTexture(textureLocation);
            Minecraft minecraft = Minecraft.getMinecraft();
            try {
                loadTexture.loadTexture(minecraft.getResourceManager());
                texture = loadTexture.getGlTextureId();
            } catch (IOException e) {
                log.error("Failed to load texture", e);
                texture = TextureUtil.missingTexture.getGlTextureId();
            }
        }
    }
}
