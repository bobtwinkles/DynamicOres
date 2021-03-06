package tk.sirtwinkles.dynores.blocks.loading;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import tk.sirtwinkles.dynores.DynamicOresMod;

import com.google.common.base.Throwables;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockWalker {
    public static void walkClass(Class c, Configuration cfg) {
        for (Field f : c.getFields()) {
            if (Modifier.isStatic(f.getModifiers()) && Block.class.isAssignableFrom(f.getType())) {
                RegisterBlock registerBlock = f.getAnnotation(RegisterBlock.class);
                if (registerBlock != null) {
                    Block b = null;
                    try {
                        b = (Block) f.get(null);
                    } catch (Exception ex) {
                        Throwables.propagate(ex);
                    }
                    b.setBlockName(registerBlock.unlocalizedName());
                    b.setBlockTextureName("dynores:" + registerBlock.textureName());
                    b.setCreativeTab(DynamicOresMod.dynoresCreativeTab);
                    GameRegistry.registerBlock(b, registerBlock.name());
                    log.info("Registered block {} with unlocalized name {} and tex ture {}", registerBlock.name(),
                            registerBlock.unlocalizedName(), registerBlock.textureName());
                } else {
                    log.warn(
                            "Field {} has correct type to be automagically registered as a block but does not have an annotation",
                            f.getName());
                }
            }
        }
    }
}
