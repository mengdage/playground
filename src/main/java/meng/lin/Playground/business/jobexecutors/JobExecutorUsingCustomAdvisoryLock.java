package meng.lin.Playground.business.jobexecutors;


import static meng.lin.Playground.business.Constants.BATCH_SIZE;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import meng.lin.Playground.business.ResultWriter;
import meng.lin.Playground.business.locking.AdvisoryLockManager;
import meng.lin.Playground.business.locking.TaskLockManager;
import meng.lin.Playground.data.NumberEntity;
import meng.lin.Playground.data.NumberRepository;

@Component
public class JobExecutorUsingCustomAdvisoryLock implements JobExecutor {

  private static final Logger LOG = LoggerFactory.getLogger(JobExecutorUsingCustomAdvisoryLock.class);

  @Autowired
  private NumberRepository numberRepository;

  @Autowired
  private AdvisoryLockManager advisoryLockManager;

  @Override
  public long run(String name) {
    TaskLockManager taskLockManager = new TaskLockManager(advisoryLockManager, "numbers_tasks_lock", name);

    if (!taskLockManager.acquireLock()) {
      LOG.info("{}: Failed to acquire the advisory lock.", name);
      return -1L;
    }

    LOG.info("{}: Acquired the advisory lock.", name);
    Random random = new Random();
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      LOG.info("{}: Interrupted while sleeping.", name);
      return 0L;
    }

    List<NumberEntity> uncompletedTasks = numberRepository.findUncompletedTasks(BATCH_SIZE);

    if (uncompletedTasks.isEmpty()) {
      LOG.info("{}: No task to execute.", name);
      taskLockManager.releaseLock();
      return 0L;
    }

    try (ResultWriter writer = new ResultWriter(name + "_custom_advisory_lock_logs")) {
      uncompletedTasks.stream().forEach(task -> {
        try {
          writer.append(task.toString() + "\n");
        } catch (IOException e) {
          e.printStackTrace();
        }

        task.setDone(true);
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    numberRepository.saveAll(uncompletedTasks);

    return uncompletedTasks.size();
  }
}
