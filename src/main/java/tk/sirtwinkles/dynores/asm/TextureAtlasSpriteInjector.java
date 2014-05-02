package tk.sirtwinkles.dynores.asm;

import static tk.sirtwinkles.dynores.DynamicOresPlugin.log;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import tk.sirtwinkles.dynores.ClientProxy;
import tk.sirtwinkles.dynores.DynamicOresMod;

public class TextureAtlasSpriteInjector implements IInjector {
    private static final String clearFramesMcp = "clearFramesTextureData";
    private static final String clearFramesSrg = "func_130103_1";
    private static final String textureAtlasSpriteMcp = "net/minecraft/client/renderer/texture/TextureAtlasSprite";
    // private static final String textureAtlasSpriteSrg = ""
    private static final String iconNameMcp = "iconName";
    private static final String iconNameSrg = "field_110984_i";

    @Override
    public void inject(ClassNode cn, boolean notObfuscated) {
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals(notObfuscated ? clearFramesMcp : clearFramesSrg)) {
                InsnList injectList = new InsnList();
                String desc = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getType(String.class));
                injectList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                injectList.add(new FieldInsnNode(Opcodes.GETFIELD, textureAtlasSpriteMcp, notObfuscated ? iconNameMcp
                        : iconNameSrg, Type.getDescriptor(String.class)));
                injectList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                        "tk/sirtwinkles/dynores/asm/TextureAtlasSpriteInjector", "shouldClearList", desc));

                LabelNode skipJump = new LabelNode(new Label());
                injectList.add(new JumpInsnNode(Opcodes.IFNE, skipJump));

                injectList.add(new InsnNode(Opcodes.RETURN));
                injectList.add(skipJump);
                mn.instructions.insert(injectList);
            }
        }
    }

    public static boolean shouldClearList(String label) {
        ClientProxy proxy = (ClientProxy) DynamicOresMod.proxy;
        log.info("Asked about texture {}", label);
        if (label.equals("stone")) {
            log.info("Prevented deleteion of metadata for texture {}", label);
            return false;
        }
        if (proxy.knownOres.contains("minecraft:" + label)) {
            log.info("Prevented deletion of metadata for texture {}", label);
            return false;
        }
        return true;
    }
}
