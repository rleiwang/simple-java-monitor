lock free java function execution stats collector
-------------------------------------------------

    1. Use configuration file to define the monitored functions at startup time
         1. set environment variable SimpleMonitorYaml="the location of the conf"
         2. the configuration file is in Yaml format
             class.name1:
               - function1
               - function2
             class.name2:
               - function1
               - function2
    2. Use OSGI WeavingHook to wrap a try/finally block around the monitored function body
    3. Collect stats in working thread w/o any lock, save the stats on ThreadLocal map
    4. A seperate aggregation thread will consolidate the result and dump to the console


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
