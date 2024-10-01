package com.metamorph.domains.file.repository;

import com.metamorph.domains.file.model.UserFile;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FileRepositoryHelper {

  private final FileRepository fileRepository;


  /**
   * Find all files.
   *
   * @param userId   the id of the user
   * @param isActive the active status of the files
   * @return the list of files
   * @throws EntityNotFoundException if the files are not found
   */
  public List<UserFile> getAll(boolean isActive, String userId) {

    List<UserFile> files = fileRepository.findAllFiles(isActive, userId);
    if (files == null || files.isEmpty()) {
      throw new EntityNotFoundException("No File found");
    }
    return files;
  }

  /**
   * Find all files.
   *
   * @param userId   the id of the user
   * @param fileId the id of the file
   * @return the file
   * @throws EntityNotFoundException if the file is not found
   */
  public UserFile getFile(Long fileId, String userId) {

    UserFile file = fileRepository.findById(fileId, userId)
        .orElseThrow(() -> new EntityNotFoundException("No File found with id: " + fileId));

    return file;
  }
}
