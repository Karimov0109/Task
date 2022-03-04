package com.example.task.controller;

import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.task.dto.FileDTO;
import com.example.task.response.Response;
import com.example.task.service.FileService;
import com.example.task.service.FileStorageService;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    private final FileService fileService;


    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    public static String generateRandomName(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    @PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file) {

        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String generatedName = currentYear + "." + currentMonth + "." + currentDay + "." + generateRandomName(15);
        Long unixTime = Instant.now().getEpochSecond();

        String extension = FileNameUtils.getExtension(file.getOriginalFilename());
//        = generatedName + "." + extension
//        file.getOriginalFilename()

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        FileDTO fileDTO = FileDTO.builder()
                .fileOriginalName(fileName)
                .fileStorage(fileDownloadUri)
                .fileSize(file.getSize())
                .fileGeneratedName(generatedName)
                .fileDate(unixTime)
                .build();

        fileService.fileAdd(fileDTO);

        return new Response(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<Response> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }
}
