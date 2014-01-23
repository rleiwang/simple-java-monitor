package org.simple.monitor;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class FunctionRepo {
  private static final ArrayList<String> repo = new ArrayList<>();
  private static final AtomicInteger cnt = new AtomicInteger(-1);

  public static int addFunctionInfo(String function) {
    repo.add(null);
    int idx = cnt.addAndGet(1);
    repo.set(idx, function);
    return idx;
  }

  public static String getFunctionInfo(int idx) {
    return repo.get(idx);
  }
}
