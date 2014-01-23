package org.simple.monitor.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.hooks.weaving.WeavingHook;
import org.simple.monitor.StatsCollector;

import java.util.Calendar;
import java.util.Hashtable;

public class Activator implements BundleActivator {

  Thread dumpThread = new Thread(new Runnable() {
    private void dump() throws Exception {
      Thread.sleep(10000);
      System.out.println("stats dumps @ " + Calendar.getInstance().getTime());
      StatsCollector.dump2Console();
    }

    @Override
    public void run() {
      while (true) {
        try {
          dump();
        } catch (Throwable e) {
          e.printStackTrace();
        }
      }
    }
  });

  @Override
  public void start(BundleContext context) throws Exception {
    System.out.println("Starting WeavingHookImpl");
    Hashtable<String, String> props = new Hashtable<>();
    ServiceRegistration sr =
            context.registerService(WeavingHook.class.getName(), new WeavingHookImpl(), props);
    dumpThread.start();
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    System.out.println("Stopping Weaving");
  }
}
