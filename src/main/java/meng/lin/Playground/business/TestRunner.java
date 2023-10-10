package meng.lin.Playground.business;


import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TestRunner implements Callable<Long> {

  private static final Logger LOG = LoggerFactory.getLogger(TestRunner.class);

  private final String name;

//  @Qualifier("jobExecutorUsingRowLock")
//  @Qualifier("jobExecutorUsingPGAdvisoryLock")
  @Qualifier("jobExecutorUsingCustomAdvisoryLock")
  @Autowired
  private JobExecutor jobExecutor;

  public TestRunner(String name) {
    this.name = name;
  }

  @Override
  public Long call() {
    long finishedCount = 0;
    while (true) {
      if (Thread.currentThread().isInterrupted()) {
        LOG.info("{} is interrupted", name);
        return finishedCount;
      }
      LOG.info("{}: Start run", name);
      long count = jobExecutor.run(name);
      if (count > 0) {
        finishedCount += count;
      }

      if (count == 0) {
        LOG.info("{}: End job", name);
        return finishedCount;
      }
      LOG.info("{}: End run", name);
    }

  }
}
