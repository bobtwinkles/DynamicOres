package tk.sirtwinkles.dynores.worldgen;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveHandler;

public class DeepDimWorldServer extends WorldServer {

    public DeepDimTeleporter teleporter;

    public DeepDimWorldServer(MinecraftServer server, ISaveHandler saveHandler, String name, int id,
            WorldSettings settings, Profiler profiler) {
        super(server, saveHandler, name, id, settings, profiler);
        teleporter = new DeepDimTeleporter(this);
    }

    @Override
    public Teleporter getDefaultTeleporter() {
        log.info("Asked for DeepDim teleporter");
        return teleporter;
    }
}
