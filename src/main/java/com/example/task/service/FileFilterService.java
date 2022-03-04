package com.example.task.service;

import com.example.task.dto.FilePageDTO;
import com.example.task.dto.PageableDto;
import com.example.task.dto.ResponsePageableDto;
import com.example.task.entity.FileEntity;
import com.example.task.repository.FileRepository;
import com.example.task.utils.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileFilterService {

    private final FileRepository fileRepository;

    public FileFilterService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<FileEntity> getOne(String fileOriginalName) {
        return fileRepository.findByFileOriginalName(fileOriginalName);
    }

    @Transactional
    public ResponsePageableDto getFilesByTime(String firstDate, String secondDate) throws ParseException {

        Long firstDate1 = Util.dateParse(firstDate);
        Long secondDate1 = Util.dateParse(secondDate);

        PageRequest page = PageRequest.of(Math.toIntExact(firstDate1), Math.toIntExact(secondDate1));
        Page<FileEntity> result = fileRepository.findByFileDateInterval(page);

        ResponsePageableDto responsePageableDto = new ResponsePageableDto();

        responsePageableDto.setPageable(new PageableDto(result.getTotalPages(),
                result.getTotalElements(),
                page.getPageNumber(),
                page.getPageSize()));
        responsePageableDto.setData(result.stream().map(FilePageDTO::new).collect(Collectors.toList()));

        return responsePageableDto;
    }

    @Transactional
    public ResponsePageableDto getFilesBySize(Long firstSize, Long secondSize) {
        PageRequest page = PageRequest.of(Math.toIntExact(firstSize), Math.toIntExact(secondSize));
        Page<FileEntity> result = fileRepository.findByFileSizeInterval(page);

        ResponsePageableDto responsePageableDto = new ResponsePageableDto();

        responsePageableDto.setPageable(new PageableDto(result.getTotalPages(),
                result.getTotalElements(),
                page.getPageNumber(),
                page.getPageSize()));
        responsePageableDto.setData(result.stream().map(FilePageDTO::new).collect(Collectors.toList()));

        return responsePageableDto;
    }
}
