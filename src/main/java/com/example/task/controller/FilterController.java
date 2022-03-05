package com.example.task.controller;

import com.example.task.dto.FilePageDTO;
import com.example.task.dto.ResponsePageableDto;
import com.example.task.service.FileFilterService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class FilterController {

    private final FileFilterService fileFilterService;

    public FilterController(FileFilterService fileFilterService) {
        this.fileFilterService = fileFilterService;
    }

    @GetMapping("/get-one")
    public List<FilePageDTO> getOneFile(@RequestParam String fileOriginalName) {
        return fileFilterService.getOne(fileOriginalName);
    }

    @GetMapping("/get-files-bytime")
    public ResponsePageableDto getFilesByTime(@RequestParam String firstDate, @RequestParam String secondDate) throws ParseException {
        return fileFilterService.getFilesByTime(firstDate, secondDate);
    }

    @GetMapping("/get-file-bySize")
    public ResponsePageableDto getFilesBySize(@RequestParam Long firstSize, @RequestParam Long secondSize){
        return fileFilterService.getFilesBySize(firstSize, secondSize);
    }
}
