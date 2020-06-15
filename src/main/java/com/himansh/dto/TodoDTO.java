package com.himansh.dto;

import com.himansh.entities.TodoEntity;
import com.himansh.entities.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class TodoDTO {
    private int todoId;
    @NotBlank(message = "Content of todo is empty")
    private String todoBody;
    private boolean isDone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date todoDate;
    private UserEntity user;

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTodoBody() {
        return todoBody;
    }

    public void setTodoBody(String todoBody) {
        this.todoBody = todoBody;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Date getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(Date todoDate) {
        this.todoDate = todoDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    //Method to convert Entity to DTO
    public static TodoDTO valueOf(TodoEntity todoEntity){
        TodoDTO todoDTO=new TodoDTO();
        todoDTO.setTodoId(todoEntity.getTodoId());
        todoDTO.setTodoBody(todoEntity.getTodoBody());
        todoDTO.setDone(todoEntity.isDone());
        todoDTO.setTodoDate(todoEntity.getTodoDate());
        //todoDTO.setUser(todoEntity.getUser());
        return todoDTO;
    }

    //Method to convert DTO to Entity
    public TodoEntity toEntity(){
        TodoEntity todoEntity=new TodoEntity();
        todoEntity.setTodoId(this.getTodoId());
        todoEntity.setTodoBody(this.getTodoBody());
        todoEntity.setDone(this.isDone());
        todoEntity.setTodoDate(this.getTodoDate());
        todoEntity.setUser(this.getUser());
        return todoEntity;
    }

    @Override
    public String toString() {
        return "["+todoId+" "+todoBody+" "+isDone+"]";
    }
}
