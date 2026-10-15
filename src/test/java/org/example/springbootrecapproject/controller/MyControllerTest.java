package org.example.springbootrecapproject.controller;

import com.jayway.jsonpath.JsonPath;
import org.example.springbootrecapproject.model.MyTask;
import org.example.springbootrecapproject.repository.MyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MyControllerTest {

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    //das ist für Anfragen von Außen um den Controller zu injizieren
    //Simuliert den Client
    // Injiziert MockMvc, um HTTP-Anfragen an den Controller zu simulieren
    @Autowired
    private MockMvc mockMvc;

    //Damit simuliere ich meine echten DatenBank
    // Repository für den Zugriff auf die Testdatenbank
    @Autowired
    private MyRepo repo;


    @Test
    void getAllTasks_ShoundReturnListOfOneTasks_WhenCalled() throws Exception {
        MyTask myTask = MyTask.builder()
                .id("1")
                .description("Task1")
                .status("Open")
                .build();

        repo.save(myTask);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                [
                                {
                                  "id": "1",
                                  "description": "Task1",
                                  "status": "Open"
                                }
                                ]
                                """
                ));
    }

    //Get all characters without injection of Repo
    @Test
    void getAllTasks_ShoundReturnListOfOneTasks_WhenCalledWithoutInjection() throws Exception {

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                           "id": "1",
                                           "description": "Task1",
                                           "status": "Open"
                                        }
                                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Generierte ID aus der JSON-Antwort auslesen
        String id = JsonPath.read(
                result.getResponse().getContentAsString(),
                "$.id");

        //Zweiteen Task posten
        MvcResult result2 = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                           "id": "2",
                                           "description": "Task2",
                                           "status": "Open"
                                        }
                                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Generierte ID aus der JSON-Antwort auslesen
        String id2 = JsonPath.read(
                result2.getResponse().getContentAsString(),
                "$.id");

        //When & Then
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                //ODER Diese Beide auskommentierten
                // .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                //.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                [
                                {
                                    "id": "%s",
                                    "description": "Task1",
                                    "status": "Open"
                                },
                                {
                                    "id": "%s",
                                    "description": "Task2",
                                    "status": "Open"
                                }
                                ]
                                
                                """.formatted(id, id2)
                ));
    }

    //Without Injection of Repo
    @Test
    void createNewTask_WithoutInjection() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                 "id": "2",
                                 "description": "Task2",
                                 "status": "Open"
                                }
                                """))
                //Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                 //"id": "2",
                                 "description": "Task2",
                                 "status": "Open"
                                }
                                """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty());

    }

    //Without Injection of Repo
    @Test
    void getTaskById_WithoutInjection() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                           "id": "1",
                                           "description": "Task1",
                                           "status": "Open"
                                        }
                                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Generierte ID aus der JSON-Antwort auslesen
        String id = JsonPath.read(
                result.getResponse().getContentAsString(),
                "$.id");

        //Zweiteen Task posten
        MvcResult result2 = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                           "id": "2",
                                           "description": "Task2",
                                           "status": "Open"
                                        }
                                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Generierte ID aus der JSON-Antwort auslesen
        String id2 = JsonPath.read(
                result2.getResponse().getContentAsString(),
                "$.id");

        //When & Then
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/" + id))
                //ODER Diese Beide auskommentierten
                // .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                //.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                    "id": "%s",
                                    "description": "Task1",
                                    "status": "Open"
                                }
                                
                                """.formatted(id)
                ));
    }

    //With Injection
    @Test
    void editTask_WitInjection() throws Exception {
        //Given
        MyTask myTask = MyTask.builder()
                .id("1")
                .description("Task1")
                .status("Open")
                .build();

        repo.save(myTask);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                 "description": "Task1_Edited",
                                 "status": "IN_PROGRESS"
                                }
                                """))
                //Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                 "id": "1",
                                 "description": "Task1_Edited",
                                 "status": "IN_PROGRESS"
                                }
                                """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty());
    }

    @Test
    void deleteTask_WithoutInjection() throws Exception {
        // Given

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": "3",
                                    "description": "Task3",
                                    "status": "Open"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //and return weil ich das was perform zrückschickt speichere MvcResult result
                .andReturn();

        // ID des angelegten Characters ermitteln
        // Dafür kannst du die POST-Antwort auslesen.
        String id = JsonPath.read(
                result.getResponse().getContentAsString(),
                "$.id"
        );

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}