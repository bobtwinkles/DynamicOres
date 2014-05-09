package tk.sirtwinkles.dynores.worldgen;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;

import java.util.Arrays;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;
import tk.sirtwinkles.dynores.DynamicOresMod;

public class DeepDimProvider extends WorldProvider {

    public DeepDimProvider() {
    }

    @Override
    protected void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManager(this.worldObj.getSeed(), WorldType.DEFAULT);
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
    
    public static int findNextWorldId() {
        Integer[] worlds = DimensionManager.getIDs(false);
        Arrays.sort(worlds);
        return worlds[0] - 1;
    }
}
