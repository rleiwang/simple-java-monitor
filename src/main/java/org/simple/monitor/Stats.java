package org.simple.monitor;

public class Stats {
  private long count;
  private long errs;
  private double duration;
  private double min = Double.MAX_VALUE;
  private long minTimestamp;
  private double max = Double.MIN_VALUE;
  private long maxTimestamp;

  public void error() {
    errs++;
  }

  public void collect(double elapsed, long timestamp) {
    count++;
    duration += elapsed;
    if (min > duration) {
      min = duration;
      minTimestamp = timestamp;
    }

    if (max < duration) {
      max = duration;
      maxTimestamp = timestamp;
    }
  }

  public void aggregate(Stats stats) {
    count += stats.getCount();
    errs += stats.getErrors();
    duration += stats.getDuration();
    if (min > stats.getMin()) {
      min = stats.getMin();
      minTimestamp = stats.getMinTimestamp();
    }

    if (max < stats.getMax()) {
      max = stats.getMax();
      maxTimestamp = stats.getMaxTimestamp();
    }
  }

  public long getErrors() {
    return errs;
  }

  public long getCount() {
    return count;
  }

  public double getDuration() {
    return duration;
  }

  public double getMin() {
    return min;
  }

  public double getMax() {
    return max;
  }

  public long getMinTimestamp() {
    return minTimestamp;
  }

  public long getMaxTimestamp() {
    return maxTimestamp;
  }
}
