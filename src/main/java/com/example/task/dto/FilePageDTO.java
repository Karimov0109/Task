package com.example.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilePageDTO {

    private Long fileSize;
    private String fileStorage;
    private Long fileDate;
    private String fileOriginalName;

}
