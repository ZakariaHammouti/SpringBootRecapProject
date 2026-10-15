package org.example.springbootrecapproject.controller;


import org.example.springbootrecapproject.dto.MyToDoDTO;
import org.example.springbootrecapproject.model.MyToDo;
import org.example.springbootrecapproject.service.MyService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class MyController {

    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }

    @GetMapping("api/todo")
    public List<MyToDo> getToDo() {
        return service.getToDo();//listMyApps;
    }

    @GetMapping("board/todo")
    public List<MyToDo> getBoardToDo() {
        return service.getBoardToDo();//listMyApps;
    }

    @PostMapping("api/todo")
    public MyToDo postToDo(@RequestBody MyToDoDTO myToDoDTO) {
        return service.postToDo(myToDoDTO);
    }

    @GetMapping("api/todo/{id}")
    public MyToDo getToDoWithId(@PathVariable String id) {
        return service.getToDoWithId(id);//listMyApps;
    }

    @PutMapping("api/todo/{id}")
    public MyToDo putToDo(@PathVariable String id,
                          @RequestBody MyToDoDTO myToDoDTO) {
        return service.putToDo(id, myToDoDTO);
    }

    @DeleteMapping("api/todo/{id}")
    public void deleteToDo(@PathVariable String id) {
        service.deleteToDo(id);
    }
}
