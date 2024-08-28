package com.example.todo.controller;

import com.example.todo.models.ToDoItem;
import com.example.todo.repository.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    TodoRepo todoRepo;

    @GetMapping("/todosList")
    public ResponseEntity<List<ToDoItem>> getTodoList(){
        try {
            ArrayList<ToDoItem> item = new ArrayList<>();
            //todoRepo.findAll().forEach(item::add);
            item.addAll(todoRepo.findAll());



            if(item.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/addTask")
    public ResponseEntity<ToDoItem> addTask(@RequestBody ToDoItem item){
        try {
            ToDoItem itemObj = todoRepo.save(item);
            return new ResponseEntity<>(itemObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<ToDoItem> deleteItem(@PathVariable long id){
        try{
            todoRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
