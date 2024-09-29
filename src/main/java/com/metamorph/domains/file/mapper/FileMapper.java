package com.metamorph.domains.file.mapper;

import com.metamorph.domains.file.dto.FileResponse;
import com.metamorph.domains.file.model.File;
import com.metamorph.domains.file.repository.FileRepository;
import com.metamorph.domains.file.dto.FileCreateRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileMapper {

  private final FileRepository fileRepository;

  public FileMapper(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  public File mapToEntity(MultipartFile file,String type, String extension, String userId)
      throws IOException {

    return File.builder()
        .name(file.getOriginalFilename())
        .type(type)
        .extension(extension)
        .created(LocalDate.now())
        .modified(null)
        .deleted(null)
        .isActive(true)
        .data(file.getBytes())
        .userId(userId)
        .build();
  }

  public FileResponse mapToDto(File file) {

    return FileResponse.builder()
        .id(file.getId())
        .name(file.getName())
        .extension(file.getExtension())
        .created(file.getCreated())
        .modified(file.getModified())
        .deleted(file.getDeleted())
        .isActive(file.isActive())
        .data(file.getData()).build();
  }

  public List<FileResponse> mapToDtoList(List<File> files) {

    return files.stream().map(this::mapToDto).toList();
  }

}
