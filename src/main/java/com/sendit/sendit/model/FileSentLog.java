package com.sendit.sendit.model;

import javax.persistence.*;

@Entity
@Table(name = "FileSentLog")
public class FileSentLog extends AbstractEntity {
    @Column(length = 15, nullable = false)
    private String sendIp;

    @Column(length = 15, nullable = false)
    private String getIp;

    @OneToOne()
    @JoinColumn(name = "file_idx")
    private File file;
}
