package com.example.task.service;

import com.example.task.dto.FileDTO;
import com.example.task.dto.FilePageDTO;
import com.example.task.dto.PageableDto;
import com.example.task.dto.ResponsePageableDto;
import com.example.task.entity.FileEntity;
import com.example.task.repository.FileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


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
                .fileOriginalName(fileDTO.getFileOriginalName().split("\\.")[0])
                .build();
        System.out.println(fileDTO.getFileOriginalName().split("\\.")[0]);
        fileRepository.save(fileEntity);
    }


    public ResponsePageableDto getFiles(int pageNumber, int pageSize) {
        PageRequest page = PageRequest.of(pageNumber, pageSize);
        Page<FileEntity> result = fileRepository.findAll(page);

        ResponsePageableDto responsePageableDto = new ResponsePageableDto();

        responsePageableDto.setPageable(new PageableDto(result.getTotalPages(),
                result.getTotalElements(),
                pageNumber,
                pageSize));
        responsePageableDto.setData(result.stream().map(FilePageDTO::new).collect(Collectors.toList()));

        return responsePageableDto;

    }

}
