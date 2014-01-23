package org.simple.monitor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatsCollector {
  private static Map<Thread, StatsCollector> collectors = new ConcurrentHashMap<>();

  public static final int METHOD_EXIT = 0;
  public static final int THROW_EXCEPTION = 1;

  private Map<Integer, Stats> methods = new HashMap<>();

  private long milli_time = 0L;
  private long nano_time = 0L;
  private long call_time = 0L;
  private int methodIdx = 0;

  public static void dump2Console() {
    for (Map.Entry<Integer, Stats> entry : getAndResetCollections().entrySet()) {
      StringBuilder sb = new StringBuilder();
      sb.append(FunctionRepo.getFunctionInfo(entry.getKey()) + " [");
      Stats sc = entry.getValue();
      sb.append("c:" + sc.getCount() + ", d:" + (sc.getDuration() / sc.getCount()) + "]");
      System.out.println(sb.toString());
    }
  }

  public static Map<Integer, Stats> getAndResetCollections() {
    Map<Integer, Stats> stats = new HashMap<>();
    for (Map.Entry<Thread, StatsCollector> entry : collectors.entrySet()) {
      Map<Integer, Stats> tmp = entry.getValue().methods;
      entry.getValue().methods = new HashMap<>();
      for (Map.Entry<Integer, Stats> kv : tmp.entrySet()) {
        Stats stat = stats.get(kv.getKey());
        if (stat == null) {
          stat = new Stats();
          stats.put(kv.getKey(), stat);
        }
        stat.aggregate(kv.getValue());
      }
    }

    return stats;
  }

  public StatsCollector() {
    collectors.put(Thread.currentThread(), this);
  }

  public void enter(int methodIdx) {
    milli_time = System.currentTimeMillis();
    nano_time = System.nanoTime();
    this.methodIdx = methodIdx;
    this.call_time = System.currentTimeMillis();
  }

  public void exit() {
    stop(METHOD_EXIT);
  }

  public void throwExp() {
    stop(THROW_EXCEPTION);
  }

  private void stop(int condition) {
    Stats stat = methods.get(methodIdx);
    if (stat == null) {
      stat = new Stats();
      methods.put(methodIdx, stat);
    }
    if (condition == THROW_EXCEPTION) {
      stat.error();
    }

    double elapse = (System.nanoTime() - nano_time) / 1000000D + (System.currentTimeMillis() - milli_time);

    stat.collect(elapse, call_time);
  }
}
