package com.sendit.sendit.dto;

import com.sendit.sendit.model.File;
import com.sendit.sendit.model.FileSentLog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileSendLogDTO {
    private String getIp;

    private String sendIp;

    private File file;

    public FileSentLog toEntity(){
        return new FileSentLog(sendIp,getIp,file);
    }
}
