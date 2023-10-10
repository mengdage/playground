package meng.lin.Playground.business;


import static meng.lin.Playground.business.Constants.BATCH_SIZE;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import meng.lin.Playground.data.NumberEntity;
import meng.lin.Playground.data.NumberRepository;

@Component
public class JobExecutorUsingRowLock implements JobExecutor {

  private static final Logger LOG = LoggerFactory.getLogger(JobExecutorUsingRowLock.class);

  @Autowired
  private NumberRepository numberRepository;

  @Override
  @Transactional
  public long run(String name) {

    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      LOG.info("{}: Interrupted while sleeping.", name);
      return 0L;
    }

    List<NumberEntity> uncompletedTasks = numberRepository.findUncompletedTasksForUpdate(BATCH_SIZE);

    if (uncompletedTasks.isEmpty()) {
      LOG.info("{}: No task to execute.", name);
      return 0L;
    }

    try (ResultWriter writer = new ResultWriter(name + "_row_lock_logs")) {
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
