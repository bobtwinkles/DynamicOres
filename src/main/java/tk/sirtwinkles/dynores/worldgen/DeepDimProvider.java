package tk.sirtwinkles.dynores.worldgen;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import tk.sirtwinkles.dynores.DynamicOresMod;

public class DeepDimProvider extends WorldProvider {

    public DeepDimProvider() {
    }

    @Override
    protected void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.desertHills, 0.1f);
        this.dimensionId = DynamicOresMod.dimId;
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        log.info("Getting deepdim chunk generator");
        return new DeepDimChunkGenerator(this.worldObj, this.worldObj.getSeed());
    }

    @Override
    public String getDimensionName() {
        return "DynOres Deep Dim";
    }

    @Override
    public String getWelcomeMessage() {
        return "Welcome to the deep";
    }
}
