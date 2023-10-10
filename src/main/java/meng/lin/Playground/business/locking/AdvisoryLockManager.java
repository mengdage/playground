package meng.lin.Playground.business.locking;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class AdvisoryLockManager {

  private final AdvisoryLockRepository repository;

  private final Supplier<Instant> clock;

  @Autowired
  public AdvisoryLockManager(AdvisoryLockRepository repository) {
    this(repository, Instant::now);
  }

  AdvisoryLockManager(AdvisoryLockRepository repository, Supplier<Instant> clock) {
    this.repository = repository;
    this.clock = clock;
  }

  public boolean acquireLock(String lockIdentifier, String lockHolderIdentifier, Duration ttl) {
    return acquireLock(lockIdentifier, lockHolderIdentifier, clock.get().plus(ttl));
  }

  public boolean acquireLock(String lockIdentifier, String lockHolderIdentifier,
      Instant expiresOn) {

    AcquireLockDbParams lockDbParams = buildAcquireLockDbParams(lockIdentifier, lockHolderIdentifier, expiresOn);

    if (tryToInsertLock(lockDbParams)) {
      return true;
    }

    if (tryToExtendLockForHolder(lockDbParams)) {
      return true;
    }

    if (tryToDeleteExpiredLock(lockIdentifier) && tryToInsertLock(lockDbParams)) {
      return true;
    }

    return false;
  }

  public boolean releaseLock(String lockIdentifier, String lockHolderIdentifier) {
    //success if 1 row deleted
    return repository.deleteLockForLockHolder(lockIdentifier, lockHolderIdentifier) == 1;
  }

  public void cleanUpExpiredLocks() {
    repository.deleteAllExpiredLocks(clock.get());
  }


  public long getCountIncludingExpired() {
    return repository.count();
  }


  private boolean tryToInsertLock(AcquireLockDbParams lockDbParams) {
    try {
      repository.insertLock(lockDbParams);
      return true;
    } catch (DataIntegrityViolationException e) {
      //duplicate key
      return false;
    }
  }

  private boolean tryToExtendLockForHolder(AcquireLockDbParams lockDbParams) {
    //success if 1 row updated
    return repository.updateLockExpiresOnForLockHolder(lockDbParams) == 1;
  }

  private boolean tryToDeleteExpiredLock(String lockIdentifier) {
    //success if 1 row deleted
    return repository.deleteExpiredLock(lockIdentifier, clock.get()) == 1;
  }

  private AcquireLockDbParams buildAcquireLockDbParams(String lockIdentifier,
      String lockHolderIdentifier, Instant expiresOn) {

    return AcquireLockDbParams.builder()
        .lockIdentifier(lockIdentifier)
        .lockHolderIdentifier(lockHolderIdentifier)
        .expiresOn(expiresOn)
        .now(clock.get())
        .build();
  }
}
