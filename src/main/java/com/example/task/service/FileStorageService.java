package com.example.task.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

import com.example.task.dto.FileDTO;
import com.example.task.exception.FileNotFoundException;
import com.example.task.exception.FileStorageException;
import com.example.task.property.FileStorageProperties;
import com.example.task.response.Response;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final FileService fileService;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties, FileService fileService) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.fileService = fileService;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalizing file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Checking if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Coping file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    public Response uploadFile( MultipartFile file){

        String extension = FileNameUtils.getExtension(file.getOriginalFilename());
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String generatedName = currentYear + "." + currentMonth + "." + currentDay + "." + generateRandomName(15) ;
        Long unixTime = Instant.now().getEpochSecond();


        try {
            byte[] bytes = file.getBytes();
            String insPath = "C:\\Users\\User\\IdeaProjects\\Task\\uploads\\" + generatedName + "." + extension; // Directory path where you want to save ;
            Files.write(Paths.get(insPath), bytes);
        }

        catch (IOException e) {
            // Handling exception here
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(generatedName+ "." + extension)
                .toUriString();

        FileDTO fileDTO = FileDTO.builder()
                .fileOriginalName(file.getOriginalFilename())
                .fileStorage(fileDownloadUri)
                .fileSize(file.getSize())
                .fileGeneratedName(generatedName)
                .fileDate(unixTime)
                .build();

        fileService.fileAdd(fileDTO);

        return new Response(file.getOriginalFilename(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    public static String generateRandomName(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
