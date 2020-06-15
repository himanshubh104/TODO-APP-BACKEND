package com.himansh.dto;

import com.himansh.entities.TodoEntity;
import com.himansh.entities.UserEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.List;

public class UserDTO {
    private int userId;
    @NotBlank(message = "Name cannot be empty")
    private String userName;
    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "[0-9A-Za-z]{6,}", message = "Password with min length 6 is required")
    private String userPassword;
    private List<TodoEntity> todos;
    private boolean authenticated;
    
    
	/*
	 * private String token;
	 * 
	 * public String getToken() { return token; }
	 * 
	 * public void setToken(String token) { this.token = token; }
	 */

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

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

    //Method to convert Entity to DTO
    public static UserDTO valueOf(UserEntity userEntity){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setUserPassword(userEntity.getUserPassword());
        userDTO.setTodos(userEntity.getTodos());
        return userDTO;
    }

    //Method to convert DTO to Entity
    public UserEntity toEntity(){
        System.out.println(this.getUserName()+" "+this.getUserPassword());
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(this.getUserId());
        userEntity.setUserName(this.getUserName());
        userEntity.setUserPassword(this.getUserPassword());
        userEntity.setTodos(userEntity.getTodos());
        return userEntity;
    }
}
