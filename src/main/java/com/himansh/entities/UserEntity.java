package com.himansh.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "user")
@SequenceGenerator(name = "user_id_seq",initialValue = 100,allocationSize = 1)
//@JsonIdentityInfo(property = "userId",
//        generator = ObjectIdGenerators.PropertyGenerator.class)
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_seq")
    @Column(name = "user_id")
    private int userId;
    @Column(name = "user_name",nullable = false,unique = true)
    private String userName;
    @Column(name = "user_password",nullable = false)
    private String userPassword;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    //@JoinColumn(name = "user_id")
    private List<TodoEntity> todos;

    @JsonManagedReference
//    @JsonBackReference
    public List<TodoEntity> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoEntity> todos) {
        this.todos = todos;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
