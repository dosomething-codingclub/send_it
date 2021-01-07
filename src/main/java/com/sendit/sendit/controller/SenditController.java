package com.sendit.sendit.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
public class SenditController {
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

}