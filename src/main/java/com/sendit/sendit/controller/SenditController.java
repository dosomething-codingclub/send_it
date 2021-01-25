package com.sendit.sendit.controller;

import com.sendit.sendit.dto.FileDTO;
import com.sendit.sendit.model.File;
import com.sendit.sendit.repository.FileRepository;
import com.sendit.sendit.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;

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
    public ResponseEntity<?> upload(MultipartHttpServletRequest mtfRequest) {

        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();//HttpServletReqeust 가져오기
        String ip = req.getHeader("X-FORWARDED-FOR");//was일 경우 ip확인
        if (ip == null)//was가 아니라면
            ip = req.getRemoteAddr();

        //서버로 넘어온 파일들을 리스트로 저장
        List<MultipartFile> fileList = mtfRequest.getFiles("file");

        fileservice.filesave(fileservice, fileList, ip);

        return ResponseEntity.ok(null);
    }

    //다운로드
    @PostMapping("/download")
    public ResponseEntity<?> download(@RequestParam("token") String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<File> temp = filerepository.findByToken(token);
        File mFile = temp.get(0);
        String orgName = mFile.getFileName();
        java.io.File file = new java.io.File("D:\\filedown", orgName);

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String fileName;

        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
            fileName = URLEncoder.encode(orgName, "UTF-8");
        } else {
            //나머지 브라우저에서 인코딩
            fileName = new String(orgName.getBytes("UTF-8"), "iso-8859-1");
        }
        //형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octet-stream");
        //다운로드와 다운로드될 파일이름
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (BufferedInputStream b_is = new BufferedInputStream(new FileInputStream(file));
             BufferedOutputStream b_os = new BufferedOutputStream(response.getOutputStream())
        ) {
            FileCopyUtils.copy(b_is, b_os);//파일 복사
            b_os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/test")
    public FileDTO test(@RequestBody FileDTO filedto) {
        fileservice.create(filedto);
        return filedto;
    }
}