package com.himansh.services;

import com.himansh.dto.TodoDTO;
import com.himansh.entities.TodoEntity;
import com.himansh.exceptions.TodoException;
import com.himansh.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    @Autowired
    @Qualifier("todoRepository")
    private TodoRepository todoRepo;

    public List<TodoDTO> getTodo(int userId) throws TodoException{
    	List<TodoEntity> teList=todoRepo.getTodoByUserId(userId);
        if (teList.size()!=0){
            return teList.stream().map(TodoDTO::valueOf).collect(Collectors.toList());
        }
        throw new TodoException("No Todos Found for "+userId);
    }
    public List<TodoDTO> getTodoByUserName(String userName) throws TodoException {
        List<TodoEntity> teList=todoRepo.getTodoByUserName(userName);
        if (teList.size()!=0){
            return teList.stream().map(TodoDTO::valueOf).collect(Collectors.toList());
        }
        throw new TodoException("No Todos Found for "+userName);
    }

    @Transactional
    public TodoDTO createTodo(TodoDTO todoDTO){
        return TodoDTO.valueOf(todoRepo.saveAndFlush(todoDTO.toEntity()));
    }

    public TodoDTO updateTodo(TodoDTO todoDTO) throws TodoException {
        if (todoRepo.findByTodoId(todoDTO.getTodoId())!=null)
            return TodoDTO.valueOf(todoRepo.saveAndFlush(todoDTO.toEntity()));
        throw new TodoException("Todo not found");
    }

    public boolean deleteTodos(int userId) throws TodoException {
        if (todoRepo.deleteTodo(userId)==0){
            throw new TodoException("Todo not found, Unable to delete");
        }
        return true;
    }
}
