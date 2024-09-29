package com.metamorph.domains.file.repository;

import com.metamorph.domains.file.model.File;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<File, Long> {

  @Query("SELECT f FROM File f WHERE f.userId = :userId AND f.isActive = :isActive")
  List<File> findAllFiles(boolean isActive, String userId);

  @Query("SELECT f FROM File f WHERE f.userId = :userId AND f.id = :fileId")
  Optional<File> findById(Long fileId, String userId);
}
