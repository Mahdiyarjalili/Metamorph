package com.metamorph.domains.importedfile.mapper;

import com.metamorph.domains.importedfile.dto.FileResponse;
import com.metamorph.domains.importedfile.enums.FileType;
import com.metamorph.domains.importedfile.model.UserFile;
import com.metamorph.domains.importedfile.repository.FileRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileMapper {

  private final FileRepository fileRepository;

  public UserFile mapToEntity(MultipartFile file,String type, String extension, String userId)
      throws IOException {

    return UserFile.builder()
        .name(file.getOriginalFilename())
        .type(FileType.valueOf(type))
        .extension(extension)
        .created(LocalDate.now())
        .modified(null)
        .deleted(null)
        .isActive(true)
        .data(file.getBytes())
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
