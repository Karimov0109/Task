package com.example.task.dto;

import com.example.task.entity.FileEntity;
import com.example.task.utils.Util;
import lombok.Data;

@Data
public class FilePageDTO {

    private Long fileSize;
    private String fileStorage;
    private String fileDate;
    private String fileOriginalName;

    public FilePageDTO(FileEntity fileEntity) {
        this.fileSize = fileEntity.getFileSize();
        this.fileStorage = fileEntity.getFileStorage();
        this.fileDate = Util.timeParse(fileEntity.getFileDate());
        this.fileOriginalName = fileEntity .getFileOriginalName();
    }

    public FilePageDTO() {

    }
}
