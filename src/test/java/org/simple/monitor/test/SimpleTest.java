package org.simple.monitor.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.simple.monitor.test.service.SimpleService;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.options;

@RunWith(PaxExam.class)
public class SimpleTest {

  @Inject
  private SimpleService simpleService;

  @Configuration
  public Option[] config() {
    return options(junitBundles());
  }

  @Test
  public void getHelloService() {
    assertNotNull(simpleService);
    assertEquals("done", simpleService.doSomethingSimple());
  }
}
