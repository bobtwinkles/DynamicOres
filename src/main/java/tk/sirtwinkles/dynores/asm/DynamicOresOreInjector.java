package tk.sirtwinkles.dynores.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import tk.sirtwinkles.dynores.DynamicOresPlugin;
import tk.sirtwinkles.dynores.renderer.OreRenderer;

public class DynamicOresOreInjector implements IInjector {
    public static final String getRenderTypeMcp = "getRenderType";
    public static final String getRenderTypeSrg = "func_149645_b";

    public void inject(ClassNode cn, boolean notObfuscated) {
        InsnList instructions = new InsnList();

        DynamicOresPlugin.log.info("Forcing " + cn.name + " renderer id to " + OreRenderer.RENDERER.getRenderId());

        instructions.add(new LdcInsnNode(OreRenderer.RENDERER.getRenderId()));
        instructions.add(new InsnNode(Opcodes.IRETURN));

        String descriptor = Type.getMethodDescriptor(Type.INT_TYPE);
        MethodNode mn = new MethodNode(Opcodes.ASM4, Opcodes.ACC_PUBLIC, notObfuscated ? getRenderTypeMcp
                : getRenderTypeSrg, descriptor, null, null);

        mn.instructions = instructions;
        mn.maxLocals = 1;
        mn.maxStack = 8;

        cn.methods.add(mn);
    }
}
