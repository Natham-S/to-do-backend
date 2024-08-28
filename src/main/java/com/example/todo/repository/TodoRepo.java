package com.example.todo.repository;
import com.example.todo.models.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepo extends JpaRepository<ToDoItem,Long> {

}
