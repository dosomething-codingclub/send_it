package com.sendit.sendit.service;

import com.sendit.sendit.dto.FileDTO;
import com.sendit.sendit.model.File;
import com.sendit.sendit.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public void filesave(FileService fileservice, List<MultipartFile> fileList, String ip) throws IOException {
        FileDTO filedto = new FileDTO();

        for (MultipartFile mf : fileList) {
            System.out.println("파일 이름 : " + mf.getOriginalFilename());
            System.out.println("파일 크기 : " + mf.getSize());

            System.out.println(ip);

            String fileName = mf.getOriginalFilename();

            //아이피 설정
            filedto.setSendIp(ip);
            //토큰 설정
            filedto.setToken(fileservice.maketoken());
            //파일명 설정
            filedto.setFileName(fileName);

            //목표 주소 생성
            Path directory = Paths.get("D:\\filedown").toAbsolutePath().normalize();
            //해당 경로까지 디렉토리 생성
            Files.createDirectories(directory);
            //파일명에 ..문자가 있을 경우 오류 발생
            Assert.state(!mf.getOriginalFilename().contains(".."),"파일 이름에서 ..을 제거하세요");
            Path targetPath = directory.resolve(fileName).normalize();
            //파일이 이미 존재한다면 오류 발생
            Assert.state(!Files.exists(targetPath),fileName+" 파일이 이미 생성되어있습니다.");

            mf.transferTo(targetPath);

            fileservice.create(filedto);
        }
    }
}