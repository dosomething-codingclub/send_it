package com.sendit.sendit.repository;

import com.sendit.sendit.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository <File,Long> {
}