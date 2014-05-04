package tk.sirtwinkles.dynores.worldgen;

import java.util.Random;

import tk.sirtwinkles.dynores.DynamicOresMod;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;

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
