package com.example.task.dto;

import com.example.task.entity.FileEntity;
import lombok.Data;

@Data
public class FilePageDTO {

    private Long fileSize;
    private String fileStorage;
    private Long fileDate;
    private String fileOriginalName;

    public FilePageDTO(FileEntity fileEntity) {
        this.fileSize = fileEntity.getFileSize();
        this.fileStorage = fileEntity.getFileStorage();
        this.fileDate = fileEntity.getFileDate();
        this.fileOriginalName = fileEntity .getFileOriginalName();
    }
}
