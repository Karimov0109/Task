package com.example.task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "file_sec")
    @SequenceGenerator(name = "file_sec", sequenceName = "file_sec", allocationSize = 1)
    private Long id;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_storage", unique = true)
    private String fileStorage;

    @Column(name = "file_date")
    private Long fileDate;

    @Column(name = "file_original_name")
    private String fileOriginalName;

    @Column(name = "file_generated_name", unique = true)
    private String fileGeneratedName;
}
