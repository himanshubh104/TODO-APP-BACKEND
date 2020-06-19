package com.himansh.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "todo")
@SequenceGenerator(name = "todo_id_seq",initialValue = 100,allocationSize = 1)
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "todo_id_seq")
    @Column(name = "todo_id")
    private int todoId;
    @Column(name = "todo_body")
    private String todoBody;
    @Column(name = "todo_isdone")
    private boolean isDone;
    @Column(name = "todo_date")
    @Temporal(TemporalType.DATE)
    private Date todoDate;
    @ManyToOne//(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @JsonBackReference
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
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

    @Override
    public String toString() {
        return "[ "+todoBody+" ]";
    }
}
