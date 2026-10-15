package org.example.springbootrecapproject.service;


import org.example.springbootrecapproject.dto.MyTaskDTO;
import org.example.springbootrecapproject.model.IdService;
import org.example.springbootrecapproject.model.MyTask;
import org.example.springbootrecapproject.repository.MyRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {

    private final IdService idService;
    private final MyRepo repo;

    public MyService(IdService idService, MyRepo repo) {
        this.idService = idService;
        this.repo = repo;
    }

    public List<MyTask> getAllTasks() {
        return repo.findAll();
    }

    public MyTask createNewTask(MyTaskDTO myTaskDTO) {
        MyTask myTask = MyTask.builder().id(idService.randomId())
                .description(myTaskDTO.description())
                .status(myTaskDTO.status())
                .build();
        return repo.save(myTask);
    }

    public MyTask getTaskById(String id) {
        return repo.findById(id).orElse(null);

    }

    public MyTask editTask(String id, MyTaskDTO myTaskDTO) {
        MyTask actualMyTask = repo.findById(id).orElse(null);

        if (actualMyTask != null) {
            return repo.save(actualMyTask
                    .withDescription(myTaskDTO.description())
                    .withStatus(myTaskDTO.status()));
        } else {
            throw new RuntimeException("id not found");
        }
    }

    public void deleteTask(String id) {
        repo.deleteById(id);
    }
}
