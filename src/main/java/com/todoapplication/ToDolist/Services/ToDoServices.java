package com.todoapplication.ToDolist.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todoapplication.ToDolist.Entity.ToDo;
import com.todoapplication.ToDolist.Repositray.ToDoRepo;

@Service
public class ToDoServices {

    @Autowired
    ToDoRepo toDoRepo;

    public ResponseEntity<List<ToDo>> getTodoList() {
        try {
            ArrayList<ToDo> todoList = new ArrayList<>();
            toDoRepo.findAll().forEach(todo -> todoList.add(todo));
            return new ResponseEntity<>(todoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getToDoById(Integer id) {
        try {
            Optional<ToDo> todo = toDoRepo.findById(id);
            if (todo.isPresent()) {
                return new ResponseEntity<>(todo.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("ToDo not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to load", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ToDo saveorupdateToDo(ToDo todo) {
        if (todo.getId() == null) {
            return toDoRepo.save(todo);
        } else {
            if (!toDoRepo.existsById(todo.getId())) {
                throw new IllegalArgumentException("ToDo with ID " + todo.getId() + " does not exist.");
            }
            return toDoRepo.save(todo);
        }
    }
    

    public void deleteToDo(Integer id) {
        toDoRepo.deleteById(id);
    }

    public Optional<ToDo> findById(Integer id) {
            return toDoRepo.findById(id);
    }
    

}
