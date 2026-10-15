package org.example.springbootrecapproject.service;


import org.example.springbootrecapproject.dto.MyToDoDTO;
import org.example.springbootrecapproject.model.IdService;
import org.example.springbootrecapproject.model.MyToDo;
import org.example.springbootrecapproject.repository.MyRepo;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MyService {

    private final IdService idService;
    private final MyRepo repo;

    public MyService(IdService idService, MyRepo repo) {
        this.idService = idService;
        this.repo = repo;
    }

    public List<MyToDo> getToDo() {
        System.out.println("getToDo Service");
        return repo.findAll();//listMyApps;
    }

    public List<MyToDo> getBoardToDo() {
        System.out.println("getBoardToDo Service");
        return repo.findAll();//listMyApps;
    }

    public MyToDo postToDo(MyToDoDTO myToDoDTO) {
        MyToDo myToDo = MyToDo.builder().id(idService.randomId())
                .description(myToDoDTO.description())
                .status(myToDoDTO.status())
                .build();
        return repo.save(myToDo);
    }

    public MyToDo getToDoWithId(String id) {
        MyToDo todo = repo.findById(id).orElse(null);
        System.out.println("Gefundenes ToDo: " + todo);
        return todo;
    }

    public MyToDo putToDo(String id, MyToDoDTO myToDoDTO) {
        System.out.println("Put From Client");

        MyToDo actualMyToDo = repo.findById(id).orElse(null);

        if (actualMyToDo != null) {
            return repo.save(actualMyToDo
                    .withDescription(myToDoDTO.description())
                    .withStatus(myToDoDTO.status()));
        } else {
            throw new RuntimeException("id not found");
        }
    }

    public void deleteToDo(String id) {
        System.out.println("delete From Client");
        repo.deleteById(id);
    }

}
