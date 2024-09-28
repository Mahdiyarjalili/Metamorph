package com.metamorph.file.controller;

import static org.springframework.http.MediaType.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import com.metamorph.file.dto.FileCreateRequest;
import com.metamorph.file.dto.FileResponse;
import com.metamorph.file.service.FileService;
import com.metamorph.util.LogMessages;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
@Slf4j
public class FileController {

  private final FileService fileService;

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createFile(@AuthenticationPrincipal Jwt jwt,
      @RequestParam("file") MultipartFile file,
      @RequestParam("type") String type) {

    String response = fileService.addFile(file,type, jwt);
    log.info(LogMessages.ENTITY_CREATED, "File", response);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping()
  public ResponseEntity<List<FileResponse>> getFiles(@RequestParam("is-active") boolean isActive,
      @AuthenticationPrincipal Jwt jwt) {

    List<FileResponse> files = fileService.getAllFiles(isActive, jwt);
    log.info(LogMessages.ENTITIES_FOUND, "Files", files);

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/{fileId}")
  public ResponseEntity<FileResponse> getFile(@PathVariable Long fileId,
      @AuthenticationPrincipal Jwt jwt) {

    FileResponse file = fileService.getFile(fileId, jwt);
    log.info(LogMessages.ENTITY_FOUND, "File", fileId);

    return ResponseEntity.status(HttpStatus.OK).body(file);
  }

  @DeleteMapping("/{fileId}")
  public ResponseEntity<FileResponse> deleteFile(@PathVariable Long fileId,
      @AuthenticationPrincipal Jwt jwt) {

    FileResponse file = fileService.getFile(fileId, jwt);
    log.info(LogMessages.ENTITY_FOUND, "File", fileId);

    return ResponseEntity.status(HttpStatus.OK).body(file);
  }

}
