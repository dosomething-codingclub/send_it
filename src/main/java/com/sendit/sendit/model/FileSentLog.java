package com.sendit.sendit.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "FileSentLog")
public class FileSentLog extends AbstractEntity {
    @Column(length = 15, nullable = false)
    private String sendIp;

    @Column(length = 15, nullable = false)
    private String getIp;

    @OneToOne()
    @JoinColumn(name = "file_idx")
    private File file;

    public FileSentLog(String sendIp, String getIp, File file){
        this.sendIp = sendIp;
        this.getIp = getIp;
        this.file = file;
    }
}
