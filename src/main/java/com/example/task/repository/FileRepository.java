package com.example.task.repository;

import com.example.task.dto.FilePageDTO;
import com.example.task.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List <FileEntity> findByFileOriginalName(String fileOriginalName);

    @Query("SELECT e FROM FileEntity e WHERE e.fileDate BETWEEN :firstDate AND :secondDate")
    List<FileEntity> findByFileDateInterval(Long firstDate, Long secondDate);

    @Query("SELECT P FROM FileEntity P WHERE P.fileSize BETWEEN :firstSize AND :secondSize")
    List<FileEntity> findByFileSizeInterval(Long firstSize, Long secondSize);

}
