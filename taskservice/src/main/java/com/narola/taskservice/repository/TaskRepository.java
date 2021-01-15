package com.narola.taskservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.narola.core.task.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	@Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Task t WHERE t.project.id = :projId AND t.user.userId = :userId AND date(t.createdOn) = CURRENT_DATE")
	boolean existsByProjectIdAndUserIdAndCurrentDate(@Param("projId") int projId, @Param("userId") int userId);

	@Query("FROM Task t WHERE t.project.id = :projId AND t.user.userId = :userId AND date(t.createdOn) = CURRENT_DATE")
	List<Task> findByProjectIdAndUserUserIdAndCreatedOn(@Param("projId") int projId, @Param("userId") int userId);

	@Query("FROM Task t WHERE t.user.userId = :userId AND date(t.createdOn) = CURRENT_DATE")
	List<Task> findByUserUserIdAndCreatedOn(@Param("userId") int userId);

	@Transactional
	void deleteByUser_UserId(int userId);

}
