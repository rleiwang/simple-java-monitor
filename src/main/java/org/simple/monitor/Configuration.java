package org.simple.monitor;

import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class Configuration {
  private static Map<String, Set<String>> _elements = new HashMap<>();

  static {
    String conf = System.getenv("Simple.Monitor.Yaml");
    if (conf != null) {
      try (Reader r = new FileReader(conf)) {
        Yaml yaml = new Yaml();
        Map<String, List<String>> content = (Map<String, List<String>>) yaml.load(r);
        for (Map.Entry<String, List<String>> entry : content.entrySet()) {
          _elements.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
      } catch (IOException ioe) {
        System.out.println("error loading " + conf);
      }
    }
  }


  public static boolean monitorClass(String clz) {
    if (clz.startsWith("org/simple/monitor")) {
      return false;
    }
    return _elements.containsKey(clz);
  }

  public static boolean monitorMethod(String clz, String method) {
    Set<String> methods = _elements.get(clz);
    if (null != methods) {
      return methods.contains(method);
    }
    return false;
  }
}
