package tk.sirtwinkles.dynores;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import tk.sirtwinkles.dynores.renderer.MaskAtlasSprite;
import tk.sirtwinkles.dynores.renderer.OreRenderer;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

    private static Logger log;
    public IIcon testingTexture;
    public TextureMap terrain;
    public ArrayList<MaskAtlasSprite> toInject;
    public Map<String, IIcon> registeredSprites;
    public Set<String> whiteList;
    public Set<String> knownOres;
    public boolean hasStiched;
    private boolean hasInjected;

    @Override
    public void preInit() {
        log = DynamicOresPlugin.log;
        MinecraftForge.EVENT_BUS.register(this);
        toInject = new ArrayList<MaskAtlasSprite>();
        registeredSprites = Maps.newHashMap();
        hasStiched = false;
        hasInjected = false;

        Configuration config = new Configuration(new File(Loader.instance().getConfigDir(), "DynamicOresClient.cfg"));
        whiteList = Sets.newHashSet(config.get("validBackings", "values",
                new String[] { "minecraft:stone", "minecraft:gravel", "minecraft:cobblestone" }).getStringList());
        
        knownOres = Sets.newHashSet(config.get("validOres", "values",
                new String[] {
                    "minecraft:iron_ore", "minecraft:coal_ore", "minecraft:diamond_ore", "minecraft:lapis_ore", "minecraft:gold_ore"
                }).getStringList());
        
        config.save();
    }

    @Override
    public void init() {
        RenderingRegistry.registerBlockHandler(OreRenderer.RENDERER);
    }

    @Override
    public void postInit() {

        for (IIcon m : registeredSprites.values()) {
            log.info("Texture {} is {}", m.getIconName(), terrain.getTextureExtry(m.getIconName()));
        }

        if (hasStiched && hasInjected) {
            log.info("We have stiched by postinit");
        }
    }

    @SubscribeEvent
    public void textureStitchEvent(TextureStitchEvent.Pre event) {
        TextureMap map = event.map;
        if (map.getTextureType() == 0) {
            if (map.getTextureExtry("dynores:testing") == null) {
                log.info("Injecting textures into texture map");
                testingTexture = map.registerIcon("dynores:testing");
            }
            for (MaskAtlasSprite m : toInject) {
                String name = m.getIconName();
                if (map.getTextureExtry(name) == null) {
                    log.info("Injecting texture {} into texture map", name);
                    // registeredSprites.put(name, map.registerIcon(name));
                    map.setTextureEntry(name, m);
                    registeredSprites.put(name, m);
                }
            }
            terrain = map;
            hasStiched = true;
            log.info("Detected terain stiched");
        }
    }

    @SubscribeEvent
    public void textureStitchPostEvent(TextureStitchEvent.Post event) {
        if (event.map.getTextureType() == 0 && hasStiched && !hasInjected && terrain.getTextureExtry("stone") != null
                && knownOres.size() > 0) {
            int[] stone = terrain.getTextureExtry("stone").getFrameTextureData(0)[0];
            for (String oreName : knownOres) {
                if (oreName.startsWith("minecraft:")) {
                    oreName = oreName.split(":")[1];
                }
                TextureAtlasSprite sprite = terrain.getTextureExtry(oreName);
                int[] spriteData = sprite.getFrameTextureData(0)[0];
                int[] maskData = new int[spriteData.length];
                int xstart = sprite.getOriginX();
                int ystart = sprite.getOriginY();
                log.info("Testing texture {} size {},{}", oreName, sprite.getIconHeight(), sprite.getIconWidth());
                for (int i = 0; i < sprite.getIconHeight() * sprite.getIconWidth(); ++i) {
                    if (spriteData[i] != stone[i]) {
                        maskData[i] = spriteData[i];
                    }
                }

                String maskName = "mask_" + oreName;
                log.info("Injecting texture with name {}", maskName);
                MaskAtlasSprite mask = new MaskAtlasSprite(maskName, maskData, sprite.getIconWidth(),
                        sprite.getIconHeight());
                toInject.add(mask);
            }
            hasInjected = true;
        }
    }
}
