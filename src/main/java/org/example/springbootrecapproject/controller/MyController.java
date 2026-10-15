package org.example.springbootrecapproject.controller;


import org.example.springbootrecapproject.dto.MyTaskDTO;
import org.example.springbootrecapproject.model.MyTask;
import org.example.springbootrecapproject.service.MyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class MyController {

    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }

    @GetMapping("/api/todo")
    public List<MyTask> getAllTasks() {
        return service.getAllTasks();//listMyApps;
    }

    @PostMapping("/api/todo")
    public MyTask createNewTask(@RequestBody MyTaskDTO myTaskDTO) {
        return service.createNewTask(myTaskDTO);
    }

    @GetMapping("/api/todo/{id}")
    public MyTask getTaskById(@PathVariable String id) {
        return service.getTaskById(id);//listMyApps;
    }

    @PutMapping("/api/todo/{id}")
    public MyTask editTask(@PathVariable String id,
                           @RequestBody MyTaskDTO myTaskDTO) {
        return service.editTask(id, myTaskDTO);
    }

    @DeleteMapping("/api/todo/{id}")
    public void deleteTask(@PathVariable String id) {
        service.deleteTask(id);
    }
}
