package com.sendit.sendit.service;

import com.sendit.sendit.dto.FileSendLogDTO;
import com.sendit.sendit.model.File;
import com.sendit.sendit.model.FileSentLog;
import com.sendit.sendit.repository.FileSendLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileSentLogService {
    private final FileSendLogRepository filesendlogrepository;

    @Autowired
    public FileSentLogService(FileSendLogRepository filesendlogrepository) {
        this.filesendlogrepository = filesendlogrepository;
    }

    public void FileSentLogCreate(String sendIp,String getIp ,File file){
        FileSendLogDTO fileSendLogDTO = new FileSendLogDTO();
        fileSendLogDTO.setSendIp(sendIp);
        fileSendLogDTO.setGetIp(getIp);
        fileSendLogDTO.setFile(file);
        FileSentLog fileSentLog = fileSendLogDTO.toEntity();
        filesendlogrepository.save(fileSentLog);
    }
}