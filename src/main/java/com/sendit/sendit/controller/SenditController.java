package com.sendit.sendit.controller;

import com.sendit.sendit.dto.FileDTO;
import com.sendit.sendit.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/test")
public class SenditController {
    private final FileService fileservice;

    @Autowired
    public SenditController(FileService fileservice) {
        this.fileservice = fileservice;
    }

    @PostMapping(value = "/upload",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload(MultipartHttpServletRequest mtfRequest) {

        //폴더 생성 여부 확인
        String path = "D:\\filedown";
        File Folder = new File(path);

        if(!Folder.exists()){
            try{
                Folder.mkdir();
                System.out.println("폴더를 생성했습니다.");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("폴더가 생성되어있습니다.");
        }


        //서버로 넘어온 파일 저장
        List<MultipartFile> fileList = mtfRequest.getFiles("file");

        for(MultipartFile mf : fileList){
            System.out.println("파일 이름 : " + mf.getOriginalFilename());
            System.out.println("파일 크기 : " + mf.getSize());

            try (
                    FileOutputStream fos = new FileOutputStream("D:/filedown/" + mf.getOriginalFilename());
                    InputStream is = mf.getInputStream();
            ) {
                int readCount = 0;
                byte[] buffer = new byte[1024];
                while ((readCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, readCount);
                }
            } catch (Exception ex) {
                throw new RuntimeException("서버 오류");
            }
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/test")
    public FileDTO test(@RequestBody FileDTO filedto){
        fileservice.create(filedto);
        return filedto;
    }
}