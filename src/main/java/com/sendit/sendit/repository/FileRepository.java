package com.sendit.sendit.repository;

import com.sendit.sendit.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository <File,Long> {
    List<File> findByToken(String token);
    List<File> findByDeleted(boolean i);
}