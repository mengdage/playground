package meng.lin.Playground.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "numbers")
public class NumberEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "val")
  private int val;

  @Column(name = "done")
  private boolean done;

  public long getId() {
    return id;
  }

  public int getVal() {
    return val;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  @Override
  public String toString() {
    return "NumberEntity{" +
        "id=" + id +
        ", val=" + val +
        ", done=" + done +
        '}';
  }
}
