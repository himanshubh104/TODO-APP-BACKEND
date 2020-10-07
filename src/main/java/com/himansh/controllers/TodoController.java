package com.himansh.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.himansh.configure.UserPrincipal;
import com.himansh.dto.TodoDTO;
import com.himansh.entities.UserEntity;
import com.himansh.exceptions.TodoException;
import com.himansh.services.TodoService;

@RestController
@RequestMapping(path = "todos")
public class TodoController {
    @Autowired
    private TodoService todoService;   
//    @Autowired
//    private HttpSession httpSession;
    private int userId;
    

    @ModelAttribute
    public void kuchBhi() throws TodoException {
    	Object principal =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	System.out.println("principal class: "+principal.getClass());
    	if (principal instanceof UserPrincipal) {
    		userId = ((UserPrincipal)principal).getUserId();
    		} 
    	else {
    		throw new TodoException("Access denied!");
    	}
    }
    
	/*
	 * @ModelAttribute public void authentication(@CookieValue(name = "auth-token")
	 * String token)throws TodoException {
	 * 
	 * String authToken=(String) httpSession.getAttribute("auth-token");
	 * if(!authToken.equals(token)) { throw new TodoException("Access denied!"); }
	 * userId=Integer.parseInt((String) httpSession.getAttribute("user-id")); }
	 */
    
    @GetMapping(path = "/",produces = {"application/json"})
    List<TodoDTO> getTodos() throws TodoException{
        return todoService.getTodo(userId);
    }

    @PostMapping(path = "/", produces = {"application/json"})
    TodoDTO createTodo(@Valid @RequestBody TodoDTO todoDTO) throws TodoException{
    	UserEntity ue=new UserEntity();
    	ue.setUserId(userId);
    	todoDTO.setUser(ue);
        return todoService.createTodo(todoDTO);
    }

    @PutMapping(path = "/", produces = {"application/json"})
    TodoDTO updateTodo(@Valid @RequestBody TodoDTO todoDTO) throws TodoException {
    	UserEntity ue=new UserEntity();
    	ue.setUserId(userId);
    	todoDTO.setUser(ue);
    	return todoService.updateTodo(todoDTO);
    }

    @DeleteMapping(path = "/",produces = {"application/json"})
    String deleteTodos() throws TodoException {
    	return todoService.deleteTodos(userId)?"Todo deleted Successfully.":"";
    }

}
