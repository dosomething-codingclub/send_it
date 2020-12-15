package com.sendit.sendit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "File")
public class File extends AbstractEntity {
    @Column(length = 40, nullable = false)
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
}
