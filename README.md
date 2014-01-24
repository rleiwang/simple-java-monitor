lock free java osgi bundle stats collector and monitor
--------------------------------------------------------

    1. Using OSGI WeavingHook to wrap a try/finally block of the monitored method
    2. Keep stats in ThreadLocal map, collects stats in working thread w/o any lock
    3. A seperate aggregation thread will aggregate the result and dump to the console


             +--------------+
             |Thread 1      |
             |      +-------+
             |      |stats 1|<----+-
             +------+-------+     |      +--------------------+
                                  |      | Aggregation Thread |
             +--------------+     |      |--------------------|
             |Thread 2      |     +------+                    |
             |      +-------+            |  final_stats       |
             |      |stats 2|<-----------+  for stat in 1..n  |
             +------+-------+            |    final_stats     |
                                         |      += stat       |
                    +                    |    reset stat      |
                    |                    |                    |
                    |                    |  log(final_stats)  |
                    +                    |                    |
             +--------------+     +------+                    |
             |Thread n      |     |      +--------------------+
             |      +-------+     |
             |      |stats n|<----+
             +------+-------+
