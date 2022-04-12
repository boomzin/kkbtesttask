package com.example.kkbtesttask.repository;

import com.example.kkbtesttask.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Task t WHERE t.id=?1")
    int delete(int id);

}
