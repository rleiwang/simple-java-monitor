package org.simple.monitor;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class MonitorMethodVisitor extends AdviceAdapter {
  private final int methodIdx;

  MonitorMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc, int methodIdx) {
    super(api, mv, access, name, desc);
    this.methodIdx = methodIdx;
  }

  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    return super.visitAnnotation(desc, visible);
  }

  @Override
  protected void onMethodEnter() {
    super.onMethodEnter();
    visitIntInsn(BIPUSH, methodIdx);
    visitMethodInsn(INVOKESTATIC,
            "org/simple/monitor/Collector",
            "enter",
            "(I)V");
  }

  @Override
  protected void onMethodExit(int opcode) {
    visitInsn(opcode != ATHROW ? ICONST_1 : ICONST_0);
    visitMethodInsn(INVOKESTATIC,
            "org/simple/monitor/Collector",
            "exit",
            "(Z)V");
    super.onMethodExit(opcode);
  }
}
