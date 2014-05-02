package tk.sirtwinkles.dynores.asm;

import java.util.Map;

import net.minecraft.launchwrapper.IClassTransformer;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import tk.sirtwinkles.dynores.DynamicOresPlugin;

import com.google.common.collect.Maps;

public class DynOresTransformer implements IClassTransformer {

    private static final Logger log = DynamicOresPlugin.log;

    private static Map<String, IInjector> injectors;
    static {
        injectors = Maps.newHashMap();
    }

    public static void registerInjector(String target, IInjector i) {
        injectors.put(target, i);
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null)
            return null;

        if (injectors.get(transformedName) != null) {
            ClassReader cr = new ClassReader(bytes);
            ClassNode cn = new ClassNode();

            cr.accept(cn, 0);

            injectors.get(transformedName).inject(cn, name.equals(transformedName));

            ClassWriter cw = new ClassWriter(cr, 0);

            cn.accept(cw);

            return cw.toByteArray();
        }

        return bytes;
    }
}
