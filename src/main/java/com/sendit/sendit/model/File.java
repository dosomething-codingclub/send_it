package com.sendit.sendit.model;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "File")
@NoArgsConstructor
public class File extends AbstractEntity {
    @Column(length = 80, nullable = false)
    private String fileName;

    @Column(length = 15, nullable = false)
    private String sendIp;

    @Column(length = 40, nullable = false)
    private String token;

    @Column()
    private boolean deleted;

    @OneToOne(mappedBy = "file")
    private FileSentLog fileSentLog;

    public String getFileName() {
        return fileName;
    }

    public String getSendIp() {
        return sendIp;
    }

    public String getToken() {
        return token;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Builder
    public File(String fileName, String sendIp, String token){
        this.fileName = fileName;
        this.sendIp = sendIp;
        this.token = token;
        this.deleted = false;
    }
}