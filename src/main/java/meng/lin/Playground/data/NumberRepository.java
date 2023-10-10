package meng.lin.Playground.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NumberRepository extends JpaRepository<NumberEntity, Long> {

  @Transactional
  @Modifying
  @Query(
      value = "UPDATE numbers SET done = FALSE WHERE done = TRUE",
      nativeQuery = true
  )
  int resetAllTasks();

  @Query(
      value = "SELECT * FROM numbers WHERE done = FALSE ORDER BY id LIMIT :limit",
      nativeQuery = true
  )
  List<NumberEntity> findUncompletedTasks(@Param("limit") Long limit);

  @Query(
      value = "SELECT * FROM numbers WHERE done = FALSE ORDER BY id LIMIT :limit FOR UPDATE SKIP LOCKED",
      nativeQuery = true
  )
  List<NumberEntity> findUncompletedTasksForUpdate(@Param("limit") Long limit);

}
