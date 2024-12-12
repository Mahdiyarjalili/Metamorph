package com.metamorph.domains.file.mapper;

import com.metamorph.domains.file.dto.FileResponse;
import com.metamorph.domains.file.enums.UserFileType;
import com.metamorph.domains.file.model.UserFile;
import com.metamorph.domains.file.repository.FileRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileMapper {

  private final FileRepository fileRepository;

  public UserFile mapToEntity(File file,UserFileType type, String extension, String userId)
      throws IOException {

    return UserFile.builder()
        .name(file.getName())
        .type(type)
        .extension(extension)
        .created(LocalDate.now())
        .modified(null)
        .deleted(null)
        .isActive(true)
        .data(Files.readAllBytes(file.toPath()))
        .userId(userId)
        .build();
  }

  public FileResponse mapToDto(UserFile file) {

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

  public List<FileResponse> mapToDtoList(List<UserFile> files) {

    return files.stream().map(this::mapToDto).toList();
  }

}
