package org.example.springbootrecapproject.model;


import lombok.Builder;
import lombok.With;

@Builder
public record MyToDo(String id,
                     @With String description,
                     @With String status) {

}
