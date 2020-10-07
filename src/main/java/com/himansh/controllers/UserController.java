package com.himansh.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.himansh.dto.UserDTO;
import com.himansh.exceptions.TodoException;
import com.himansh.services.UserService;

@RestController
@RequestMapping(path = "users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;
    

    @GetMapping(path = "/welcome")
    public ResponseEntity<String> welcome(){
    	return ResponseEntity.ok("Welcome User");
    }

    @PostMapping(path = "/create",consumes = {"application/json"},produces = "application/json")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) throws TodoException {
    	UserDTO fetchedUser=userService.createUser(user);
       // httpSession.setAttribute("user-id", fetchedUser.getUserId()+"");
        
        fetchedUser.setUserPassword(null);
        fetchedUser.setTodos(null);
        fetchedUser.setAuthenticated(true);
        return ResponseEntity.ok().body(fetchedUser);
    }

    @DeleteMapping(path = "/",consumes = {"application/json"},produces = "application/json")
    public UserDTO removeUser(@Valid @RequestBody UserDTO user) {
        //Currently Not Working
        return user;
    }

    @GetMapping(path = "/login")
    public Map<String,String> login(){
        Map<String,String> map= new HashMap<>();
        map.put("message","Its a lovely day");
        return map;
    }


    @GetMapping(path = "/logout")
    public Map<String,Boolean> logout(){
        httpSession.invalidate();
        Map<String,Boolean> map= new HashMap<>();
        map.put("logout",true);
        return map;
    }
}
