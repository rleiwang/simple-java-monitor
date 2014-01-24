package org.simple.monitor.osgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.hooks.weaving.WeavingHook;
import org.osgi.framework.hooks.weaving.WovenClass;
import org.osgi.framework.wiring.BundleWiring;
import org.simple.monitor.Configuration;
import org.simple.monitor.MonitorClassWriter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WeavingHookImpl implements WeavingHook {
  private static String PACKAGE_NAME = "org.simple.monitor";

  private HashMap<Long, Set<String>> loaded = new HashMap<>();

  @Override
  public void weave(WovenClass clz) {
    String clzName = clz.getClassName();
    if (clzName.startsWith(PACKAGE_NAME) ||
       !Configuration.monitorClass(clzName)) {
      return;
    }

    BundleWiring bundleWiring = clz.getBundleWiring();
    Bundle bundle = bundleWiring.getBundle();
    Set<String> loadedClzs = loaded.get(bundle.getBundleId());
    if (loadedClzs == null) {
      loadedClzs = new HashSet<>();
      loaded.put(bundle.getBundleId(), loadedClzs);
    }

    if (loadedClzs.contains(clzName)) {
      return;
    }
    loadedClzs.add(clz.getClassName());

    List<String> imports = clz.getDynamicImports();
    if (!new HashSet<String>(imports).contains(PACKAGE_NAME)) {
      imports.add(PACKAGE_NAME);
    }
    String info = bundle.getSymbolicName() + ":" + bundle.getVersion().getQualifier();
    clz.setBytes(MonitorClassWriter.rewrite(clzName, info, clz.getBytes()));
  }
}