package com.himansh.controllers;

import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
  /*  @GetMapping(path = "/",produces = "application/json")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }*/
 /*   @GetMapping(path = "/{userName}",produces = "application/json")
    public UserDTO getUsers(@PathVariable("userName") String userName) throws TodoException {
        return userService.getUser(userName);
    }*/

    @PostMapping(path = "/",consumes = {"application/json"},produces = "application/json")
    public ResponseEntity<UserDTO> userLogin(@Valid @RequestBody UserDTO user) throws TodoException {
    	if(httpSession.getAttribute("auth-token")!=null) {
    		throw new TodoException("Already logedin with another account");
    	}
        UserDTO fetchedUser=userService.userLogin(user);
        UUID token=UUID.randomUUID();
        httpSession.setAttribute("auth-token", token.toString());
        httpSession.setAttribute("user-id", fetchedUser.getUserId()+"");
        
        fetchedUser.setUserPassword(null);
        fetchedUser.setTodos(null);
        fetchedUser.setAuthenticated(true);
        ResponseCookie cookie =ResponseCookie.from("auth-token", token.toString())//.sameSite("None").secure(true)
        		.httpOnly(false).path("/").build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(fetchedUser);
    }

    @GetMapping(path = "/welcome")
    public ResponseEntity<String> welcome(){
    	return ResponseEntity.ok("Welcome User");
    }
    
    @GetMapping(path = "/logout")
    public ResponseEntity<Boolean> userLogout() throws TodoException{
    	if(httpSession.getAttribute("auth-token")==null) {
    		throw new TodoException("Please Login first");
    	}
    	httpSession.invalidate();
        ResponseCookie cookie =ResponseCookie.from("auth-token",null).path("/").maxAge(0).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(true);
    }

    @PostMapping(path = "/create",consumes = {"application/json"},produces = "application/json")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) throws TodoException {
    	UserDTO fetchedUser=userService.createUser(user);
    	UUID token=UUID.randomUUID();
        ResponseCookie cookie =ResponseCookie.from("auth-token", token.toString())
        		.httpOnly(false).path("/").build();
    	httpSession.setAttribute("auth-token", token.toString());
        httpSession.setAttribute("user-id", fetchedUser.getUserId()+"");
        
        fetchedUser.setUserPassword(null);
        fetchedUser.setTodos(null);
        fetchedUser.setAuthenticated(true);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(fetchedUser);
    }

    @DeleteMapping(path = "/",consumes = {"application/json"},produces = "application/json")
    public UserDTO removeUser(@Valid @RequestBody UserDTO user) {
        //Currently Not Working
        return user;
    }
}
