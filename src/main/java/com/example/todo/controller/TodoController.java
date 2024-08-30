package com.example.todo.controller;

import com.example.todo.models.ToDoItem;
import com.example.todo.repository.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    TodoRepo todoRepo;

    @GetMapping("/taskList")
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

    @GetMapping("/taskId/{id}")
    public ResponseEntity<ToDoItem> getTodoItem(@PathVariable long id){
        try {
            Optional<ToDoItem> item = todoRepo.findById(id);
            if(item.isPresent()){
                return new ResponseEntity<>(item.get(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
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



    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<ToDoItem> deleteItem(@PathVariable long id){
        try{
            todoRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateTask/{id}")
    public  ResponseEntity<ToDoItem> updateItem(@PathVariable long id, @RequestBody ToDoItem item){
        try {
            Optional<ToDoItem> oldItem = todoRepo.findById(id);
            if(oldItem.isPresent()){
                ToDoItem updateItemData = oldItem.get();
                updateItemData.setTitle(item.getTitle());
                updateItemData.setStatus(item.getStatus());
                updateItemData.setDateTime(item.getDateTime());

                ToDoItem toDoItemObj = todoRepo.save(updateItemData);
                return new ResponseEntity<>(updateItemData, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
