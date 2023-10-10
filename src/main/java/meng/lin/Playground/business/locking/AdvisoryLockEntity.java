package meng.lin.Playground.business.locking;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "advisory_locks")
public class AdvisoryLockEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "lock_identifier")
  private String lockIdentifier;

  @Column(name = "created_on")
  private Instant createdOn;

  @Column(name = "expires_on")
  private Instant expiresOn;

  @Column(name = "lock_holder_identifier")
  private String lockHolderIdentifier;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLockIdentifier() {
    return lockIdentifier;
  }

  public void setLockIdentifier(String lockIdentifier) {
    this.lockIdentifier = lockIdentifier;
  }

  public Instant getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Instant createdOn) {
    this.createdOn = createdOn;
  }

  public Instant getExpiresOn() {
    return expiresOn;
  }

  public void setExpiresOn(Instant expiresOn) {
    this.expiresOn = expiresOn;
  }

  public String getLockHolderIdentifier() {
    return lockHolderIdentifier;
  }

  public void setLockHolderIdentifier(String lockHolderIdentifier) {
    this.lockHolderIdentifier = lockHolderIdentifier;
  }
}
