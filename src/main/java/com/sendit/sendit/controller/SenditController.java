package com.sendit.sendit.controller;

import com.sendit.sendit.dto.FileDTO;
import com.sendit.sendit.model.File;
import com.sendit.sendit.repository.FileRepository;
import com.sendit.sendit.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

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
    private final FileRepository filerepository;

    @Autowired
    public SenditController(FileService fileservice, FileRepository filerepository) {
        this.fileservice = fileservice;
        this.filerepository = filerepository;
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
    @GetMapping ("/download/{token}")
    public ResponseEntity<Resource> download(@PathVariable String token) throws MalformedURLException {
        File file = filerepository.findByToken(token).get(0);
        String orgName = file.getFileName();

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