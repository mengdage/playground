package meng.lin.Playground.business.locking;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@SuppressWarnings("checkstyle:linelength") // keep long line for readability
public interface AdvisoryLockRepository extends JpaRepository<AdvisoryLockEntity, Long> {

  @Modifying
  @Transactional
  @Query(
      value = "INSERT INTO advisory_locks(lock_identifier, created_on, expires_on, lock_holder_identifier) VALUES("
          + ":#{#params.lockIdentifier},"
          + ":#{#params.now},"
          + ":#{#params.expiresOn},"
          + ":#{#params.lockHolderIdentifier})",
      //JpaRepository only supports upsert and inserts have to be native queries
      nativeQuery = true
  )
  void insertLock(@Param("params") AcquireLockDbParams params);

  @Modifying
  @Transactional
  @Query(
      value = "UPDATE advisory_locks SET expires_on = :#{#params.expiresOn} WHERE "
          + "lock_identifier = :#{#params.lockIdentifier} AND "
          + "lock_holder_identifier = :#{#params.lockHolderIdentifier} AND "
          + "expires_on > :#{#params.now}",
      //native to share parameter class with insert
      nativeQuery = true
  )
  int updateLockExpiresOnForLockHolder(@Param("params") AcquireLockDbParams params);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM advisory_locks WHERE lock_identifier = :lockIdentifier AND expires_on < :now",
      nativeQuery = true)
  int deleteExpiredLock(@Param("lockIdentifier") String lockIdentifier, @Param("now") Instant now);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM advisory_locks WHERE expires_on < ?1",
      nativeQuery = true)
  int deleteAllExpiredLocks(Instant now);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM advisory_locks WHERE lock_identifier = ?1 AND lock_holder_identifier = ?2",
      nativeQuery = true)
  int deleteLockForLockHolder(String lockIdentifier, String lockHolder);

  AdvisoryLockEntity findByLockIdentifier(String lockIdentifier);
}
