package com.todoapplication.ToDolist.Repositray;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapplication.ToDolist.Entity.ToDo;

@Repository
public interface ToDoRepo extends JpaRepository<ToDo , Integer> {

}
