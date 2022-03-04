package com.example.task.dto;

import lombok.Data;

@Data
public class ResponsePageableDto {
    private Object data;
    private PageableDto pageable;
}
