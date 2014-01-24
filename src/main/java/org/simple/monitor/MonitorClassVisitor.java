package org.simple.monitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MonitorClassVisitor extends ClassVisitor {
  private String clzName = null;
  private String clzInfo = null;

  public MonitorClassVisitor(int api, ClassVisitor cv, String clz, String desc) {
    super(api, cv);
    clzName = clz;
    clzInfo = desc;
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    if ((name.startsWith("<") && name.endsWith(">")) ||
        !Configuration.monitorMethod(clzName, name)) {
      return super.visitMethod(access, name, desc, signature, exceptions);
    }
    String funcInfo = clzInfo + ":" + clzName + ":" + name + ":" + desc;
    return new MonitorMethodVisitor(Opcodes.ASM4,
            super.visitMethod(access, name, desc, signature, exceptions),
            access, name, desc, FunctionRepo.addFunctionInfo(funcInfo));
  }
}
