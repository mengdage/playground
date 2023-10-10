package meng.lin.Playground.business.locking;

import java.time.Instant;

public class AcquireLockDbParams {

  private final String lockIdentifier;
  private final String lockHolderIdentifier;
  private final Instant expiresOn;
  private final Instant now;

  private AcquireLockDbParams(Builder builder) {
    this.lockIdentifier = builder.lockIdentifier;
    this.now = builder.now;
    this.expiresOn = builder.expiresOn;
    this.lockHolderIdentifier = builder.lockHolderIdentifier;
  }

  public String getLockIdentifier() {
    return lockIdentifier;
  }

  public String getLockHolderIdentifier() {
    return lockHolderIdentifier;
  }

  public Instant getExpiresOn() {
    return expiresOn;
  }

  public Instant getNow() {
    return now;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String lockIdentifier;
    private String lockHolderIdentifier;
    private Instant expiresOn;
    private Instant now;

    public Builder lockIdentifier(String lockIdentifier) {
      this.lockIdentifier = lockIdentifier.toString();
      return this;
    }

    public Builder lockHolderIdentifier(String lockHolderIdentifier) {
      this.lockHolderIdentifier = lockHolderIdentifier.toString();
      return this;
    }

    public Builder expiresOn(Instant expiresOn) {
      this.expiresOn = expiresOn;
      return this;
    }

    public Builder now(Instant now) {
      this.now = now;
      return this;
    }

    public AcquireLockDbParams build() {
      return new AcquireLockDbParams(this);
    }
  }
}
