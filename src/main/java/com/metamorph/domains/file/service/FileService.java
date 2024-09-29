package com.metamorph.domains.file.service;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import com.metamorph.file.dto.FileResponse;
import com.metamorph.file.mapper.FileMapper;
import com.metamorph.file.model.File;
import com.metamorph.file.repository.FileRepository;
import com.metamorph.file.repository.FileRepositoryHelper;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

  private final FileRepository fileRepository;
  private final FileMapper fileMapper;
  private final FileRepositoryHelper fileRepositoryHelper;

  public String addFile(MultipartFile file,String type,Jwt jwt) {
    String userId = jwt.getSubject();
    String fileExtension = getFileExtension(file);
    try {
      fileRepository.save(fileMapper.mapToEntity(file,type,fileExtension, userId));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "File created successfully";
  }

  public List<FileResponse> getAllFiles(boolean isActive, Jwt jwt) {

    String userId = jwt.getSubject();
    List<File> files = fileRepositoryHelper.getAll(isActive, userId);

    return fileMapper.mapToDtoList(files);
  }

  public Map<String, Object> getFile(Long fileId, Jwt jwt) {

    String userId = jwt.getSubject();
    Map<String, Object> fileData = new HashMap<>();
    File file = fileRepositoryHelper.getFile(fileId, userId);
    fileData.put("name",file.getName());
    fileData.put("length",file.getData().length);
    fileData.put("data",file.getData());


    return fileData;
  }

  public String deleteFile(Long fileId, Jwt jwt) {

    String userId = jwt.getSubject();
    File file = fileRepositoryHelper.getFile(fileId, userId);
    fileRepository.delete(file);

    return "File deleted successfully";
  }
public String getFileExtension(MultipartFile file)
{
  return FilenameUtils.getExtension(file.getOriginalFilename());
}
}
