package org.simple.monitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class MonitorClassWriter {
  public static byte[] rewrite(String name, String desc, byte[] clz) {
    ClassReader classReader = new ClassReader(clz);
    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
    classReader.accept(new MonitorClassVisitor(Opcodes.ASM4, cw, name, desc),
            ClassReader.EXPAND_FRAMES | ClassReader.SKIP_DEBUG);
    return cw.toByteArray();
  }
}
