package com.example.task.controller;

import com.example.task.dto.ResponsePageableDto;
import com.example.task.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class FileController {

    final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/api/1.0/files")
    public ResponsePageableDto getFiles(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "5") int pageSize){
        return fileService.getFiles(pageNumber, pageSize);
    }
}

