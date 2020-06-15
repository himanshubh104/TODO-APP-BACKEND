package com.himansh.services;

import com.himansh.dto.UserDTO;
import com.himansh.entities.UserEntity;
import com.himansh.exceptions.TodoException;
import com.himansh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepo;

    public List<UserDTO> getUsers(){
        return userRepo.findAll().stream().map(UserDTO::valueOf).collect(Collectors.toList());
    }
    public UserDTO getUser(String name) throws TodoException {

        UserEntity ue=userRepo.findByUserName(name);
        if (ue==null){
            System.out.println("value fetched "+ue);
            throw new TodoException("User not found!");
        }
        return UserDTO.valueOf(ue);
    }

    public UserDTO createUser(UserDTO userDTO) throws TodoException {
        if (userRepo.findByUserName(userDTO.getUserName())!=null){
            throw new TodoException("User already exists");
        }
        String pass=userDTO.getUserPassword();
        pass=Base64.getEncoder().encodeToString(pass.getBytes());
        userDTO.setUserPassword(pass);
        return UserDTO.valueOf(userRepo.saveAndFlush(userDTO.toEntity()));
    }
    //User Login
    public UserDTO userLogin(UserDTO userDTO) throws TodoException {
        UserEntity ue=userRepo.findByUserName(userDTO.getUserName());
        if (ue==null){
            throw new TodoException("User not found!");
        }
        else {
//        	String pass=Base64.getDecoder().decode(ue.getUserPassword().getBytes()).toString();
        	String pass=ue.getUserPassword();
            if (pass.equals(Base64.getEncoder().encodeToString(userDTO.getUserPassword().getBytes())))
                return UserDTO.valueOf(ue);
        }
        throw new TodoException("Wrong password");
    }
}
