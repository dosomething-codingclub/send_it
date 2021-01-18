package com.sendit.sendit.dto;

import com.sendit.sendit.model.File;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileDTO {

    private String sendIp;

    private String fileName;

    private String token;

    public File toEntity(){
        return File.builder()
                .sendIp(sendIp)
                .fileName(fileName)
                .token(token)
                .build();
    }
}
