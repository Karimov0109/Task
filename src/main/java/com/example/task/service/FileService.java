package com.example.task.service;

import com.example.task.dto.FileDTO;
import com.example.task.entity.FileEntity;
import com.example.task.repository.FileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class FileService {

    final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void fileAdd(FileDTO fileDTO) {
    FileEntity fileEntity = FileEntity.builder()
            .fileSize(fileDTO.getFileSize())
            .fileGeneratedName(fileDTO.getFileGeneratedName())
            .fileDate(fileDTO.getFileDate())
            .fileStorage(fileDTO.getFileStorage())
            .fileOriginalName(fileDTO.getFileOriginalName())
            .build();
    fileRepository.save(fileEntity);
    }

    public Page<FileEntity> getFiles(Pageable page){
        return fileRepository.findAll(page);

    }

    public Page<FileEntity> getFiles1(int pageNumber, int pageSize){
        PageRequest page = PageRequest.of(pageNumber, pageSize);

        return fileRepository.findAllFiles(page);

    }

}
