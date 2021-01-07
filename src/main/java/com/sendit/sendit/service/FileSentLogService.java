package com.sendit.sendit.service;

import com.sendit.sendit.model.FileSentLog;
import com.sendit.sendit.repository.FileSendLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileSentLogService {
    private final FileSendLogRepository filesendlogrepository;

    @Autowired
    public FileSentLogService(FileSendLogRepository filesendlogrepository) {
        this.filesendlogrepository = filesendlogrepository;
    }

    public List<FileSentLog> getAllFileSentLog() {
        return filesendlogrepository.findAll();
    }
}