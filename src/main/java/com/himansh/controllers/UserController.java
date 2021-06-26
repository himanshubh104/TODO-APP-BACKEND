package com.himansh.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.himansh.configure.UserPrincipal;
import com.himansh.dto.UserDTO;
import com.himansh.exceptions.TodoException;
import com.himansh.services.UserService;
import com.himansh.utils.JWTUtil;

@RestController
@RequestMapping(path = "users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;
    

    @GetMapping(path = "/welcome")
    public ResponseEntity<String> welcome(){
    	return ResponseEntity.ok("Welcome User");
    }

    @PostMapping(path = "/create",consumes = {"application/json"},produces = "application/json")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) throws TodoException {
    	UserDTO fetchedUser=userService.createUser(user);        
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

    @PostMapping(path = "/login",consumes = "application/json")
    public Map<String,Object> login(@RequestBody UserDTO user)throws Exception{
    	try {
    		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword()));
		} catch (BadCredentialsException e) {
			throw new TodoException("Incorrect Username or Password");
		}
    	UserPrincipal userDetails=(UserPrincipal) userService.loadUserByUsername(user.getUserName());
    	String jwt=jwtUtil.generateToken(userDetails);
        Map<String,Object> map= new HashMap<>();
        map.put("authenticated",true);
        map.put("jwt",jwt);
        return map;
    }


    @GetMapping(path = "/logout")
    public Map<String,Boolean> logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
        Map<String,Boolean> map= new HashMap<>();
        map.put("logout",true);
        return map;
    }
}
