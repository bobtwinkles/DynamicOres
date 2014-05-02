package tk.sirtwinkles.dynores;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tk.sirtwinkles.dynores.asm.DynOresTransformer;
import tk.sirtwinkles.dynores.asm.IInjector;
import tk.sirtwinkles.dynores.asm.TextureAtlasSpriteInjector;
import tk.sirtwinkles.dynores.asm.Transformer;
import tk.sirtwinkles.dynores.blocks.DynamicOresOreInjector;

import com.google.common.base.Throwables;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion("1.7.2")
public class DynamicOresPlugin implements IFMLLoadingPlugin {

    public static Logger log;

    static {
        log = LogManager.getLogger("DynOres");
    }

    private static boolean readInjectors;

    // Transformers
    @Transformer(target = "net.minecraft.block.BlockOre")
    public static IInjector oreBlockTransformer = new DynamicOresOreInjector();

    @Transformer(target = "net.minecraft.client.renderer.texture.TextureAtlasSprite")
    public static IInjector atlasSpriteTransformer = new TextureAtlasSpriteInjector();

    @Override
    public String[] getASMTransformerClass() {
        if (!readInjectors) {
            Class<IInjector> injectorClass = IInjector.class;
            for (Field f : DynamicOresPlugin.class.getFields()) {
                if (Modifier.isStatic(f.getModifiers()) && injectorClass.isAssignableFrom(f.getType())) {
                    Transformer annotation = f.getAnnotation(Transformer.class);
                    if (annotation != null) {
                        try {
                            IInjector entry = (IInjector) f.get(null);
                            if (entry != null) {
                                DynOresTransformer.registerInjector(annotation.target(), entry);
                                log.info("Registered class transformer {} for {}", entry.getClass().getName(),
                                        annotation.target());
                            } else {
                                log.warn("Tried to register IInjector for {} but it was null =(", annotation.target());
                            }
                        } catch (Exception e) {
                            Throwables.propagate(e);
                        }
                    } else {
                        log.warn("Field {} has the proper type for IInjector registration, but no annotation",
                                f.getName());
                    }
                }
            }
            readInjectors = true;
        }

        return new String[] { "tk.sirtwinkles.dynores.asm.DynOresTransformer" };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
