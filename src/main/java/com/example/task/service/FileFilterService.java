package com.example.task.service;

import com.example.task.dto.FilePageDTO;
import com.example.task.dto.PageableDto;
import com.example.task.dto.ResponsePageableDto;
import com.example.task.entity.FileEntity;
import com.example.task.repository.FileRepository;
import com.example.task.utils.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileFilterService {

    private final FileRepository fileRepository;

    public FileFilterService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    private static List<FilePageDTO> convertEntityDto(List<FileEntity> fileEntities) {
        List<FilePageDTO> newList = new ArrayList<>();

        for (FileEntity fileEntity : fileEntities) {
            FilePageDTO filePageDTO = new FilePageDTO();
            filePageDTO.setFileSize(fileEntity.getFileSize());
            filePageDTO.setFileDate(Util.timeParse(fileEntity.getFileDate()));
            filePageDTO.setFileStorage(fileEntity.getFileStorage());
            filePageDTO.setFileOriginalName(fileEntity.getFileOriginalName());
            newList.add(filePageDTO);
        }
        return newList;
    }


    public List<FilePageDTO> getOne(String fileOriginalName) {
        return convertEntityDto(fileRepository.findByFileOriginalName(fileOriginalName));
    }


    @Transactional
    public ResponsePageableDto getFilesByTime(int pageNumber, int pageSize, String firstDate, String secondDate) throws ParseException {

        Long firstDate1 = Util.dateParse(firstDate);
        Long secondDate1 = Util.dateParse(secondDate);

        PageRequest page = PageRequest.of(pageNumber, pageSize);
        List<FileEntity> result = fileRepository.findByFileDateInterval(firstDate1, secondDate1);

        final Page<FileEntity> page1 = new PageImpl<>(result);

        ResponsePageableDto responsePageableDto = new ResponsePageableDto();

        responsePageableDto.setPageable(new PageableDto(page1.getTotalPages(),
                page1.getTotalElements(),
                page.getPageNumber(),
                page.getPageSize()));
        responsePageableDto.setData(result.stream().map(FilePageDTO::new).collect(Collectors.toList()));

        return responsePageableDto;
    }

    @Transactional
    public ResponsePageableDto getFilesBySize(int pageNumber, int pageSize, Long firstSize, Long secondSize) {
        PageRequest page = PageRequest.of(pageNumber, pageSize);
        List<FileEntity> result = fileRepository.findByFileSizeInterval(firstSize, secondSize);

        final Page<FileEntity> page2 = new PageImpl<>(result);

        ResponsePageableDto responsePageableDto = new ResponsePageableDto();

        responsePageableDto.setPageable(new PageableDto(page2.getTotalPages(),
                page2.getTotalElements(),
                page.getPageNumber(),
                page.getPageSize()));
        responsePageableDto.setData(result.stream().map(FilePageDTO::new).collect(Collectors.toList()));

        return responsePageableDto;
    }
}
