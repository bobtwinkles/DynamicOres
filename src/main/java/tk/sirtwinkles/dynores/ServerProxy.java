package tk.sirtwinkles.dynores;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;

public class ServerProxy extends CommonProxy {
    @Override
    public void preInit() {
        log.warn("You don't need me here, I only affect clients!");
    }
}
