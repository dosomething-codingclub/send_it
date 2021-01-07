package com.sendit.sendit.repository;

import com.sendit.sendit.model.FileSentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSendLogRepository extends JpaRepository <FileSentLog,Long> {
}
