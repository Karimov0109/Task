package com.example.task.dto;


import com.example.task.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {

    private Long fileSize;
    private String fileStorage;
    private Long fileDate;
    private String fileOriginalName;
    private String fileGeneratedName;

    public FileDTO(FileEntity fileEntity) {
        this.fileSize = fileEntity.getFileSize();
        this.fileStorage = fileEntity.getFileStorage();
        this.fileDate = fileEntity.getFileDate();
        this.fileOriginalName = fileEntity.getFileOriginalName();
        this.fileGeneratedName = fileEntity.getFileGeneratedName();
    }
}
