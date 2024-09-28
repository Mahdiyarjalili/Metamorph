package com.metamorph.file.controller;

import com.metamorph.file.dto.FileCreateRequest;
import com.metamorph.file.dto.FileResponse;
import com.metamorph.file.model.File;
import com.metamorph.file.repository.FileRepository;
import com.metamorph.file.service.FileService;
import com.metamorph.util.LogMessages;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
@Slf4j
public class FileController {

  private final FileService fileService;

  @PostMapping()
  public ResponseEntity<String> createFile(@AuthenticationPrincipal Jwt jwt,
      @RequestBody @Valid FileCreateRequest fileCreateRequest) {

    String response = fileService.addFile(fileCreateRequest, jwt);
    log.info(LogMessages.ENTITY_CREATED, "File",response);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping()
  public ResponseEntity<List<FileResponse>> getFiles(@RequestParam("is-active") boolean isActive,
      @AuthenticationPrincipal Jwt jwt) {

    List<FileResponse> files = fileService.getAllFiles(isActive, jwt);
    log.info(LogMessages.ENTITIES_FOUND, "Files",files);


    return ResponseEntity.status(HttpStatus.OK).body(files);
  }
  @GetMapping("/{fileId}")
  public ResponseEntity<FileResponse> getFile(@PathVariable Long fileId,
      @AuthenticationPrincipal Jwt jwt) {

    FileResponse file = fileService.getFile(fileId, jwt);
    log.info(LogMessages.ENTITY_FOUND, "File",fileId);


    return ResponseEntity.status(HttpStatus.OK).body(file);
  }

  @DeleteMapping("/{fileId}")
  public ResponseEntity<FileResponse> deleteFile(@PathVariable Long fileId,
      @AuthenticationPrincipal Jwt jwt) {

    FileResponse file = fileService.getFile(fileId, jwt);
    log.info(LogMessages.ENTITY_FOUND, "File",fileId);


    return ResponseEntity.status(HttpStatus.OK).body(file);
  }

}
