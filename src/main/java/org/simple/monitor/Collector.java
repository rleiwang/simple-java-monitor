package org.simple.monitor;

public class Collector {
  private static ThreadLocal<StatsCollector> _mlStats = new ThreadLocal<StatsCollector>() {

    @Override
    protected StatsCollector initialValue() {
      return new StatsCollector();
    }
  };

  public static void enter(int methodIdx) {
    _mlStats.get().enter(methodIdx);
  }

  public static void exit(boolean success) {
    _mlStats.get().exit();
  }

  public static void throwExp() {
    _mlStats.get().throwExp();
  }
}
