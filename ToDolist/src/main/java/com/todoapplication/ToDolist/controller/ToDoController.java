package com.todoapplication.ToDolist.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.todoapplication.ToDolist.Entity.ToDo;
import com.todoapplication.ToDolist.Services.ToDoServices;

@RestController
@RequestMapping("todo")
public class ToDoController {

    @Autowired
    ToDoServices toDoServices;

    @GetMapping("/")
    public ResponseEntity<List<ToDo>> viewToDoList(){
        return toDoServices.getTodoList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> editTodoItem(@PathVariable Integer id) {
        return toDoServices.getToDoById(id);
    
    }

    @PostMapping("/")
    public ResponseEntity<?> addTodoItem(@RequestBody ToDo payload) {
        try{
        ToDo enterData = toDoServices.saveorupdateToDo(payload);
        return new ResponseEntity<>(enterData,HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to Create",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public String deleteToDoItem(@PathVariable Integer id) {
        toDoServices.deleteToDo(id);
        return "deleted";
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@RequestBody ToDo todo, @PathVariable Integer id) {
        Optional<ToDo> optionalToDo = toDoServices.findById(id);

        if (optionalToDo.isPresent()) {
            ToDo existingToDo = optionalToDo.get();
            existingToDo.setStatus(todo.getStatus());
            existingToDo.setTitle(todo.getTitle());
            ToDo updatedToDo = toDoServices.saveorupdateToDo(existingToDo);
            return new ResponseEntity<>(updatedToDo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
