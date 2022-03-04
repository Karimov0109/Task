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

    @Query("SELECT p.fileDate as date, p.fileOriginalName as filename, p.fileSize as size, p.fileStorage as downloadlink FROM FileEntity p ")
    Page<FileEntity> findAllFiles(PageRequest page);
}
