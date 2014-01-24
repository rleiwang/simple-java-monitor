package org.simple.monitor.test;

import org.junit.Assert;
import org.junit.Test;
import org.simple.monitor.Configuration;

public class ConfigurationTest {

  @Test
  public void testConf() {
    Assert.assertFalse(Configuration.monitorClass("org.abc"));
    Assert.assertTrue(Configuration.monitorClass("org.abc.bcd"));
    Assert.assertTrue(Configuration.monitorMethod("org.efg.hij", "efg"));
    Assert.assertFalse(Configuration.monitorMethod("org.efg.hij", "chd"));
  }
}
