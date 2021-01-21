package com.sendit.sendit.controller;

import com.sendit.sendit.dto.FileDTO;
import com.sendit.sendit.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
@RequestMapping("/test")
public class SenditController {
    private final FileService fileservice;

    @Autowired
    public SenditController(FileService fileservice) {
        this.fileservice = fileservice;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        System.out.println("파일 이름 : " + file.getOriginalFilename());
        System.out.println("파일 크기 : " + file.getSize());

        try (
                FileOutputStream fos = new FileOutputStream("c:/filedown/" + file.getOriginalFilename());
                InputStream is = file.getInputStream();
        ) {
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while ((readCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, readCount);
            }
        } catch (Exception ex) {
            throw new RuntimeException("file Save Error");
        }


        return "uploadok";
    }

    @PostMapping("/test")
    public FileDTO test(@RequestBody FileDTO filedto){
        fileservice.create(filedto);
        return filedto;
    }
}