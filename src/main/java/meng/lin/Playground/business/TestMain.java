package meng.lin.Playground.business;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import meng.lin.Playground.data.NumberRepository;

@Component
public class TestMain implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(TestMain.class);

  private final NumberRepository numberRepository;
  private final List<TestRunner> testRunners;

  @Autowired
  public TestMain(NumberRepository numberRepository, List<TestRunner> testRunners) {
    this.numberRepository = numberRepository;
    this.testRunners = testRunners;
  }

  public void start() throws InterruptedException, ExecutionException {
    ExecutorService executor = Executors.newFixedThreadPool(5);

    long start = System.nanoTime();
    List<Future<Long>> futures = executor.invokeAll(testRunners);

    for (Future<Long> future : futures) {
      Long count = future.get();
      LOG.info("Count of task completed {}", count);
    }

    executor.shutdown();
    LOG.info("Stop Test: {}ms", (System.nanoTime() - start) / 1000000);
  }

  @Override
  public void run(String... args) throws Exception {

    LOG.info("Reset tasks...");
    int countTasksInitialized = numberRepository.resetAllTasks();
    LOG.info("{} tasks are initialized", countTasksInitialized);

    start();
  }
}
