package com.himansh.repository;

import com.himansh.entities.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.Tuple;

public interface TodoRepository extends JpaRepository<TodoEntity,Integer> {
    @Query("select t from todo t where t.user.userId=:id")
    List<TodoEntity> getTodoByUserId(@Param("id") int userId);

    @Query("select t from todo t join t.user u where u.userName=:name")
    List<TodoEntity> getTodoByUserName(@Param("name") String username);

    TodoEntity findByTodoId(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    /*alternate:
     delete from todo t where t.todoId=:id and t.user in (select u from user u where u.userId=:userId)")*/
	/*
	 * @Query("delete from todo t where t.todoId=:id and t.user.userId=:userId") int
	 * deleteTodo(@Param("userId") int userId,@Param("id") int todoId);
	 */
    
    @Query("delete from todo t where t.isDone=true and t.user.userId=:userId")
    int deleteTodo(@Param("userId") int userId);
    
    @Query("select t.todoId as todo_id, t.todoBody as todo_body, t.isDone as is_done from todo t where t.user.userId = :userId")
    List<Tuple> getTodoIdandBody(Integer userId);
}
