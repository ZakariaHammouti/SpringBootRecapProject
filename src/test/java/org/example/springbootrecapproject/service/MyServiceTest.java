package org.example.springbootrecapproject.service;

import org.example.springbootrecapproject.dto.MyTaskDTO;
import org.example.springbootrecapproject.model.IdService;
import org.example.springbootrecapproject.model.MyTask;
import org.example.springbootrecapproject.repository.MyRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyServiceTest {

    @Test
    void getAllTasks_ShouldReturnListOfTasks_ByFullList() {
        //Given
        List<MyTask> tasksList = List.of(
                new MyTask("1", "Task1", "OPEN"),
                new MyTask("2", "Task2", "IN_PROGRESS"),
                new MyTask("3", "Task3", "DONE"));

        //Mockito
        MyRepo mockRepo = mock(MyRepo.class);
        IdService mockIdService = mock(IdService.class);
        MyService service = new MyService(mockIdService, mockRepo);

        //When & Then
        when(mockRepo.findAll()).thenReturn(tasksList);

        List<MyTask> actualList = service.getAllTasks();

        assertEquals(tasksList, actualList);
        verify(mockRepo).findAll();
    }

    @Test
    void getAllTasks_ShouldReturnNull_ByEmptyList() {
        //Given
        List<MyTask> tasksList = new ArrayList<>() {
        };

        //Mockito
        MyRepo mockRepo = mock(MyRepo.class);
        IdService mockIdService = mock(IdService.class);
        MyService service = new MyService(mockIdService, mockRepo);

        //When & Then
        when(mockRepo.findAll()).thenReturn(tasksList);

        List<MyTask> actualList = service.getAllTasks();

        assertTrue(actualList.isEmpty());
        verify(mockRepo).findAll();
    }

    @Test
    void createNewTask_ShouldReturnAddedTask_WithMockedId() {
        //Given
        MyTaskDTO myTaskDTO = MyTaskDTO.builder()
                .description("Task1")
                .status("Open")
                .build();
        MyTask myTask = MyTask.builder()
                .id("1")
                .description("Task1")
                .status("Open")
                .build();

        //Mockito
        MyRepo mockRepo = mock(MyRepo.class);
        IdService mockIdService = mock(IdService.class);
        MyService service = new MyService(mockIdService, mockRepo);

        //When & Then
        //wenn save mit irgend n task aufgerufen wird
        //dann gib den Mock task zurück
        when(mockIdService.randomId()).thenReturn("1");
        when(mockRepo.save(any(MyTask.class)))
                .thenReturn(myTask);

        MyTask actualTask = service.createNewTask(myTaskDTO);

        // Then
        assertEquals(actualTask, myTask);
        verify(mockIdService).randomId();
        verify(mockRepo).save(argThat(savedTask ->
                savedTask.id().equals("1")
                        && savedTask.description().equals("Task1")
                        && savedTask.status().equals("Open")
        ));
    }

    @Test
    void createNewTask_ShouldReturnAddedTask_WithNotMockedId() {
        //Given
        MyTaskDTO myTaskDTO = MyTaskDTO.builder()
                .description("Task1")
                .status("Open")
                .build();
        MyTask myTask = MyTask.builder()
                .id("1")
                .description("Task1")
                .status("Open")
                .build();

        //Mockito
        MyRepo mockRepo = mock(MyRepo.class);
        IdService mockIdService = mock(IdService.class);
        MyService service = new MyService(mockIdService, mockRepo);

        //When & Then
        //wenn save mit irgend n task aufgerufen wird
        //dann gib den Mock task zurück
        when(mockRepo.save(any(MyTask.class)))
                .thenReturn(myTask);

        MyTask actualTask = service.createNewTask(myTaskDTO);

        // Then
        assertEquals(actualTask, myTask);
        verify(mockIdService).randomId();
        verify(mockRepo).save(any(MyTask.class));
    }

    @Test
    void getTaskById_ShouldReturnTask_ByGivenValidId() {
        MyTask myTask = MyTask.builder()
                .id("1")
                .description("Task1")
                .status("Open")
                .build();

        //Mockito
        MyRepo mockRepo = mock(MyRepo.class);
        IdService mockIdService = mock(IdService.class);
        MyService service = new MyService(mockIdService, mockRepo);

        //When & Then
        when(mockRepo.findById("1")).thenReturn(Optional.of(myTask));

        MyTask actualTask = service.getTaskById("1");

        // Then
        assertEquals(actualTask, myTask);
        assertNotNull(actualTask);
        verify(mockRepo).findById("1");
    }

    @Test
    void getTaskById_ShouldReturnNull_ByGivenNotExistId() {

        //Mockito
        MyRepo mockRepo = mock(MyRepo.class);
        IdService mockIdService = mock(IdService.class);
        MyService service = new MyService(mockIdService, mockRepo);

        //When & Then
        when(mockRepo.findById("2")).thenReturn(Optional.empty());

        MyTask actualTask = service.getTaskById("2");

        // Then
        //assertEquals(actualTask, myTask);
        assertNull(actualTask);
        verify(mockRepo).findById("2");

    }

    @Test
    void editTask_ShouldReturnUpdatedTask_ByGivenValidTask() {
        //Given
        MyTaskDTO myTaskDTO = MyTaskDTO.builder()
                .description("Description Updated")
                .status("In_Progress")
                .build();

        MyTask myTask = MyTask.builder()
                .id("1")
                .description("Task1")
                .status("Open")
                .build();

        MyTask updatedTask = MyTask.builder()
                .id("1")
                .description("Description Updated")
                .status("In_Progress")
                .build();

        //Mockito
        MyRepo mockRepo = mock(MyRepo.class);
        IdService mockIdService = mock(IdService.class);
        MyService service = new MyService(mockIdService, mockRepo);

        //When & Then
        //wenn save mit irgend n task aufgerufen wird
        //dann gib den Mock task zurück
        //wenn save mit irgend n AsterixCharacter aufgerufen wird
        //dann gib den Mock character zurück
        //When
        when(mockRepo.findById("1")).thenReturn(Optional.of(myTask));
        when(mockRepo.save(any(MyTask.class)))
                .thenReturn(updatedTask);

        MyTask actualTask = service.editTask("1", myTaskDTO);

        // Then
        assertEquals(actualTask, updatedTask);

        verify(mockRepo).save(argThat(saved ->
                saved.id().equals("1")
                        && saved.description().equals("Description Updated")
                        && saved.status().equals("In_Progress")
        ));
    }

    @Test
    void editTask_ShouldReturnThrowException_ByGivenInvalidTask() {
        //Given
        MyTaskDTO myTaskDTO = MyTaskDTO.builder()
                .description("Description Updated")
                .status("In_Progress")
                .build();

        //Mockito
        MyRepo mockRepo = mock(MyRepo.class);
        IdService mockIdService = mock(IdService.class);
        MyService service = new MyService(mockIdService, mockRepo);

        //When & Then
        when(mockRepo.findById("2")).thenReturn(Optional.empty());

        try {
            service.editTask("2", myTaskDTO);
            fail("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            assertEquals("id not found", e.getMessage());
        }

        verify(mockRepo, never()).save(any(MyTask.class));
        verify(mockRepo, never()).save(any(MyTask.class));
    }

    @Test
    void deleteTask() {
        // Given
        String id = "1";

        //Mockito
        MyRepo mockRepo = mock(MyRepo.class);
        IdService mockIdService = mock(IdService.class);
        MyService service = new MyService(mockIdService, mockRepo);

        // When
        service.deleteTask(id);

        // Then
        verify(mockRepo).deleteById("1");
    }
}