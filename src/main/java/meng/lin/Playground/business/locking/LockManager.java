package meng.lin.Playground.business.locking;

public interface LockManager {

  boolean acquireLock();

  void releaseLock();
}
