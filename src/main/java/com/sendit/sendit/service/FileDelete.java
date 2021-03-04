package com.sendit.sendit.service;

import com.sendit.sendit.model.File;
import com.sendit.sendit.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class FileDelete {
    private final FileRepository fileRepository;

    @Autowired
    public FileDelete(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    public void delete(File dFile){
        String fileName = dFile.getFileName();
        java.io.File rFile = new java.io.File("D:/filedown/"+fileName);
        
        if(rFile.exists()){
            if(rFile.delete()){
                System.out.println(fileName+" 파일이 삭제되었습니다.");
            }else{
                System.out.println(fileName+" 파일 삭제가 실패하였습니다.");
            }
        }else{
            System.out.println(fileName+" 파일이 존재하지않습니다.");
        }
        dFile.setDeleted(true);
        fileRepository.save(dFile);
    }

    @Scheduled(cron = "* * * * * *")
    public void autoDelete(){
        List<File> fileList = fileRepository.findByDeleted(false);
        for (File file : fileList){
            Date cDate = file.getCreatedAt();
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            cal1.setTime(cDate);
            cal1.add(Calendar.MINUTE,10);
            cal2.setTime(new Date());

            if(cal1.getTime().before(cal2.getTime())){
                delete(file);
            }
        }
    }
}
