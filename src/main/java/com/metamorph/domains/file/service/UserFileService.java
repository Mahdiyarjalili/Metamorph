package com.metamorph.domains.file.service;

import com.metamorph.domains.file.dto.FileResponse;
import com.metamorph.domains.file.enums.UserFileType;
import com.metamorph.domains.file.mapper.FileMapper;
import com.metamorph.domains.file.model.UserFile;
import com.metamorph.domains.file.repository.FileRepository;
import com.metamorph.domains.file.repository.FileRepositoryHelper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserFileService {

  private final FileRepository fileRepository;
  private final FileMapper fileMapper;
  private final FileRepositoryHelper fileRepositoryHelper;

  public String addFile(File file, UserFileType type, Jwt jwt) throws Exception {
    String userId = jwt.getSubject();
    String fileExtension = getFileExtension(file);

    fileRepository.save(fileMapper.mapToEntity(file, type, fileExtension, userId));

    return "File created successfully";
  }

  public List<FileResponse> getAllFiles(boolean isActive, Jwt jwt) {

    String userId = jwt.getSubject();
    List<UserFile> files = fileRepositoryHelper.getAll(isActive, userId);

    return fileMapper.mapToDtoList(files);
  }

  public Map<String, Object> getFile(Long fileId, Jwt jwt) {

    String userId = jwt.getSubject();
    Map<String, Object> fileData = new HashMap<>();
    UserFile file = fileRepositoryHelper.getFile(fileId, userId);
    fileData.put("name", file.getName());
    fileData.put("length", file.getData().length);
    fileData.put("data", file.getData());

    return fileData;
  }

  public String deleteFile(Long fileId, Jwt jwt) {

    String userId = jwt.getSubject();
    UserFile file = fileRepositoryHelper.getFile(fileId, userId);
    fileRepository.delete(file);

    return "File deleted successfully";
  }

  public String getFileExtension(File file) {
    return FilenameUtils.getExtension(file.getName());
  }

  public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
    File tempFile = File.createTempFile("upload", multipartFile.getOriginalFilename());
    multipartFile.transferTo(tempFile);
    return tempFile;
  }
}
