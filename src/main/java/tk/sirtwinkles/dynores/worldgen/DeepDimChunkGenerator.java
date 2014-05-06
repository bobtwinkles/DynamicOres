package tk.sirtwinkles.dynores.worldgen;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.MapGenCaves;

public class DeepDimChunkGenerator implements IChunkProvider {
    
    private World world;
    private long seed;
    private Random rand;
    private MapGenCaves caves;

    public DeepDimChunkGenerator(World world, long seed) {
        this.world = world;
        this.seed = seed;
        this.caves = new MapGenCaves();
        this.rand = new Random();
    }

    @Override
    public boolean chunkExists(int var1, int var2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ) {
        this.rand.setSeed((long)chunkX * 341873128712L + (long)chunkZ * 132897987541L);
        Block[] blocks = new Block[65536];
        byte[] bytes = new byte[65536];
        for (int i = 0; i < blocks.length; ++i) {
            blocks[i] = Blocks.stone;
        }
        this.caves.func_151539_a(this, this.world, chunkX, chunkZ, blocks);
        Chunk tr = new Chunk(this.world, blocks, bytes, chunkX, chunkZ);
        tr.generateSkylightMap();
        tr.generateHeightMap();
        return tr;
    }

    @Override
    public Chunk loadChunk(int var1, int var2) {
        return provideChunk(var1, var2);
    }

    @Override
    public void populate(IChunkProvider chunkProvider, int chunkX, int chunkY) {
        BlockFalling.fallInstantly = true;
        
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean saveChunks(boolean var1, IProgressUpdate var2) {
        return true;
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "DeepDimOre";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType var1, int var2, int var3, int var4) {
        return Lists.newArrayList();
    }

    @Override
    public ChunkPosition func_147416_a(World var1, String var2, int var3, int var4, int var5) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int var1, int var2) {
    }

    @Override
    public void saveExtraData() {
    }
    
}
