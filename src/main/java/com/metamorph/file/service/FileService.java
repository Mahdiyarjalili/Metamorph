package com.metamorph.file.service;

import com.metamorph.file.dto.FileCreateRequest;
import com.metamorph.file.dto.FileResponse;
import com.metamorph.file.mapper.FileMapper;
import com.metamorph.file.model.File;
import com.metamorph.file.repository.FileRepository;
import com.metamorph.file.repository.FileRepositoryHelper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

  private final FileRepository fileRepository;
  private final FileMapper fileMapper;
  private final FileRepositoryHelper fileRepositoryHelper;

  public String addFile(FileCreateRequest fileCreateRequest, Jwt jwt) {
    String userId = jwt.getSubject();
    fileRepository.save(fileMapper.mapRequestToEntity(fileCreateRequest, userId));
    return "File created successfully";
  }

  public List<FileResponse> getAllFiles(boolean isActive, Jwt jwt) {

    String userId = jwt.getSubject();
    List<File> files = fileRepositoryHelper.getAll(isActive, userId);

    return fileMapper.mapToDtoList(files);
  }

  public FileResponse getFile(Long fileId, Jwt jwt) {

    String userId = jwt.getSubject();
    File file = fileRepositoryHelper.getFile(fileId, userId);

    return fileMapper.mapToDto(file);
  }

  public String deleteFile(Long fileId, Jwt jwt) {

    String userId = jwt.getSubject();
    File file = fileRepositoryHelper.getFile(fileId, userId);
    fileRepository.delete(file);

    return "File deleted successfully";
  }

}
