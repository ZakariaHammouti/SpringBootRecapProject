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

    private MyToDo myApp = new MyToDo("", "Des1", "Status1");
    private MyToDo myApp2 = new MyToDo("", "Des2", "Status2");

    List<MyToDo> listMyApps = new ArrayList<>();

    public MyController(MyService service) {
        this.service = service;
        listMyApps.add(myApp);
        listMyApps.add(myApp2);

    }

    @GetMapping("api/todo")
    public List<MyToDo> getToDo() {
        System.out.println("getToDo");
        return service.getToDo();//listMyApps;
    }

    @GetMapping("board/todo")
    public List<MyToDo> getBoardToDo() {
        System.out.println("getBoardToDo");
        return service.getBoardToDo();//listMyApps;
    }

    @PostMapping("api/todo")
    public MyToDo postToDo(@RequestBody MyToDoDTO myToDoDTO) {
        System.out.println("Post From Client");
        return service.postToDo(myToDoDTO);
    }

    @GetMapping("api/todo/{id}")
    public MyToDo getToDoWithId(@PathVariable String id) {
        System.out.println("getToDowithId");
        return service.getToDoWithId(id);//listMyApps;
    }

    @PutMapping("api/todo/{id}")
    public MyToDo putToDo(@PathVariable String id,
                          @RequestBody MyToDoDTO myToDoDTO) {
        System.out.println("Put From Client");
        return service.putToDo(id, myToDoDTO);
    }
}
