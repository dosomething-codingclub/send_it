package com.sendit.sendit.service;

import com.sendit.sendit.dto.FileDTO;
import com.sendit.sendit.model.File;
import com.sendit.sendit.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

@Service
public class FileService {

    private final FileRepository filerepository;

    @Autowired
    public FileService(FileRepository filerepository) {
        this.filerepository = filerepository;
    }

    public List<File> getAllFile() {
        return filerepository.findAll();
    }

    public void create(FileDTO filedto) {
        File file = filedto.toEntity();
        filerepository.save(file);
    }

    public String maketoken() {
        String token = "";
        List<File> temp;

        //중복된 토큰 확인 및 생성
        while (true) {
            if (token == "") {
                for (int i = 0; i < 6; i++) {
                    token += Integer.toString(new Random().nextInt(9));
                }
                temp = filerepository.findByToken(token);

                if (temp.isEmpty()) {
                    break;
                } else {
                    token = "";
                }
            }
        }
        return token;
    }

    public void mkdir() {
        //폴더 생성 여부 확인
        String path = "D:\\filedown";
        java.io.File Folder = new java.io.File(path);

        if (!Folder.exists()) {
            try {
                Folder.mkdir();
                System.out.println("폴더를 생성했습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void filesave(FileService fileservice, List<MultipartFile> fileList, String ip) {
        //저장을 위한 폴더 생성
        fileservice.mkdir();

        FileDTO filedto = new FileDTO();

        for (MultipartFile mf : fileList) {
            System.out.println("파일 이름 : " + mf.getOriginalFilename());
            System.out.println("파일 크기 : " + mf.getSize());

            System.out.println(ip);
            //아이피 설정
            filedto.setSendIp(ip);
            //토큰 설정
            filedto.setToken(fileservice.maketoken());
            //파일명 설정
            filedto.setFileName(mf.getOriginalFilename());

            try (
                    FileOutputStream fos = new FileOutputStream("D:/filedown/" + mf.getOriginalFilename());
                    InputStream is = mf.getInputStream();
                    BufferedOutputStream b_fos = new BufferedOutputStream(fos, 1024);
                    BufferedInputStream b_is = new BufferedInputStream(is, 1024)
            ) {
                int readCount = 0;
                byte[] buffer = new byte[1024];
                while ((readCount = b_is.read(buffer)) != -1) {
                    b_fos.write(buffer, 0, readCount);
                }
            } catch (Exception ex) {
                throw new RuntimeException("서버 오류");
            }
            fileservice.create(filedto);
        }
    }
}