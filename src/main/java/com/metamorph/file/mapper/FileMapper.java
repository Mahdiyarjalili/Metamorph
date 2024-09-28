package com.metamorph.file.mapper;

import com.metamorph.file.dto.FileCreateRequest;
import com.metamorph.file.dto.FileResponse;
import com.metamorph.file.model.File;
import com.metamorph.file.repository.FileRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

  private final FileRepository fileRepository;

  public FileMapper(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  public File mapRequestToEntity(FileCreateRequest fileCreateRequest, String userId) {

    return File.builder()
        .name(fileCreateRequest.getName())
        .type(fileCreateRequest.getType())
        .extention(fileCreateRequest.getExtention())
        .created(fileCreateRequest.getCreated())
        .modified(fileCreateRequest.getModified())
        .deleted(fileCreateRequest.getDeleted())
        .isActive(fileCreateRequest.isActive())
        .userId(userId)
        .build();
  }

  public FileResponse mapToDto(File file) {

    return FileResponse.builder()
        .id(file.getId())
        .name(file.getName())
        .extention(file.getExtention())
        .created(file.getCreated())
        .modified(file.getModified())
        .deleted(file.getDeleted())
        .isActive(file.isActive()).build();
  }

  public List<FileResponse> mapToDtoList(List<File> files) {

    return files.stream().map(this::mapToDto).toList();
  }

}
