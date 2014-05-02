package tk.sirtwinkles.dynores.asm;

import org.objectweb.asm.tree.ClassNode;

public interface IInjector {
    public void inject(ClassNode cn, boolean notObfuscated);
}
