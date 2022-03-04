package com.example.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableDto {
    private Integer totalPages;
    private Long totalElements;
    private Integer pageNumber;
    private Integer pageSize;
}
