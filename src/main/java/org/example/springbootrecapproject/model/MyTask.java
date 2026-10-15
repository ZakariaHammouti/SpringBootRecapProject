package org.example.springbootrecapproject.model;


import lombok.Builder;
import lombok.With;

@Builder
public record MyTask(String id,
                     @With String description,
                     @With String status) {

}
