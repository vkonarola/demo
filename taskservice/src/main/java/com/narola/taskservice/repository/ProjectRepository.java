package com.narola.taskservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.narola.core.task.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Project p WHERE p.projectName = :projName")
	boolean existsByProjectName(@Param("projName") String projName);

	List<Project> findByUsers_UserId(int userId);
}
