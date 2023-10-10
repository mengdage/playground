package meng.lin.Playground.business.locking;

import java.time.Duration;
import java.util.UUID;

public class TaskLockManager implements LockManager {
  private static final String DEFAULT_LOCK_ID_PREFIX = "DELAYED_QUEUE_LOCK";

  private final String lockId;
  private final String lockHolderIdentifier;
  private final AdvisoryLockManager advisoryLockManager;
  private final Duration expiration;

  public TaskLockManager(AdvisoryLockManager advisoryLockManager, String lockId, String identifier) {
    this.advisoryLockManager = advisoryLockManager;
    this.lockId = String.format("%s_%s", DEFAULT_LOCK_ID_PREFIX, lockId);
    this.lockHolderIdentifier = identifier;
    this.expiration = Duration.ofMinutes(10);
  }

  @Override
  public boolean acquireLock() {
    return advisoryLockManager.acquireLock(lockId, lockHolderIdentifier, expiration);
  }

  @Override
  public void releaseLock() {
    advisoryLockManager.releaseLock(lockId, lockHolderIdentifier);
  }
}
