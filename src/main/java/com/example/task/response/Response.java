package com.example.task.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Response {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

}
