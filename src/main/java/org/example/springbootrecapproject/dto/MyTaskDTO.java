package org.example.springbootrecapproject.dto;

import lombok.Builder;

@Builder
public record MyTaskDTO(String description, String status) {

}
