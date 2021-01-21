package com.sendit.sendit.service;

import com.sendit.sendit.dto.FileDTO;
import com.sendit.sendit.model.File;
import com.sendit.sendit.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileRepository filerepository;

    @Autowired
    public FileService(FileRepository filerepository){
        this.filerepository = filerepository;
    }

    public List<File> getAllFile(){
        return filerepository.findAll();
    }

    public void create(FileDTO filedto){
        File file = filedto.toEntity();
        filerepository.save(file);
    }
}