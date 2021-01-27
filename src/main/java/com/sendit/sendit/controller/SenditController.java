package com.sendit.sendit.controller;

import com.sendit.sendit.dto.FileDTO;
import com.sendit.sendit.model.File;
import com.sendit.sendit.repository.FileRepository;
import com.sendit.sendit.repository.FileSendLogRepository;
import com.sendit.sendit.service.FileSentLogService;
import com.sendit.sendit.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/test")
public class SenditController {
    private final FileService fileservice;
    private final FileSentLogService fileSentLogService;
    private final FileRepository filerepository;
    private final FileSendLogRepository fileSendLogRepository;

    @Autowired
    public SenditController(FileService fileservice, FileSentLogService fileSentLogService, FileRepository filerepository, FileSendLogRepository fileSendLogRepository) {
        this.fileservice = fileservice;
        this.fileSentLogService = fileSentLogService;
        this.filerepository = filerepository;
        this.fileSendLogRepository = fileSendLogRepository;
    }

    //업로드
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload(MultipartHttpServletRequest mtfRequest, HttpServletRequest req) throws IOException {

        String ip = req.getHeader("X-FORWARDED-FOR");//was일 경우 ip확인
        if (ip == null)//was가 아니라면
            ip = req.getRemoteAddr();

        //서버로 넘어온 파일들을 리스트로 저장
        List<MultipartFile> fileList = mtfRequest.getFiles("file");
        fileservice.filesave(fileservice, fileList, ip);

        return ResponseEntity.ok(null);
    }

    //다운로드
    @GetMapping ("/download")
    public ResponseEntity<Resource> download(HttpServletRequest req) throws MalformedURLException {
        String ip = req.getHeader("X-FORWARDED-FOR");//was일 경우 ip확인
        if (ip == null)//was가 아니라면
            ip = req.getRemoteAddr();
        String token = req.getParameter("token");

        File file = filerepository.findByToken(token).get(0);
        String orgName = file.getFileName();
        fileSentLogService.FileSentLogCreate(file.getSendIp(),ip,file);//전송완료 로그 생성

        Path filePath = Paths.get("D:\\filedown").toAbsolutePath().resolve(orgName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + orgName + "\""
                )
                .body(resource);
    }

    @PostMapping("/test")
    public FileDTO test(@RequestBody FileDTO filedto) {
        fileservice.create(filedto);
        return filedto;
    }
}