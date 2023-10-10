package meng.lin.Playground.business;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import meng.lin.Playground.data.NumberRepository;

@Configuration
public class TestConfiguration {

  @Bean
  public TestRunner testRunner1 () {
    return new TestRunner("testRunner1");
  }

  @Bean
  public TestRunner testRunner2 () {
    return new TestRunner("testRunner2");
  }
//  @Bean
//  public TestRunner testRunner3 () {
//    return new TestRunner("testRunner3");
//  }
//  @Bean
//  public TestRunner testRunner4 () {
//    return new TestRunner("testRunner4");
//  }
//  @Bean
//  public TestRunner testRunner5 () {
//    return new TestRunner("testRunner5");
//  }
}
