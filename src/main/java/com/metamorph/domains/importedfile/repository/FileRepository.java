package com.metamorph.domains.importedfile.repository;

import com.metamorph.domains.importedfile.model.UserFile;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<UserFile, Long> {

  @Query("SELECT f FROM File f WHERE f.userId = :userId AND f.isActive = :isActive")
  List<UserFile> findAllFiles(boolean isActive, String userId);

  @Query("SELECT f FROM File f WHERE f.userId = :userId AND f.id = :fileId")
  Optional<UserFile> findById(Long fileId, String userId);
}
