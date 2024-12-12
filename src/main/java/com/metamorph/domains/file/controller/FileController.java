package com.metamorph.domains.file.controller;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import com.metamorph.domains.file.dto.FileResponse;
import com.metamorph.domains.file.enums.UserFileType;
import com.metamorph.domains.file.service.UserFileService;
import com.metamorph.util.LogMessages;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
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

  private final UserFileService userFileService;

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createFile(@AuthenticationPrincipal Jwt jwt,
      @RequestParam("file") MultipartFile file) throws Exception {
    File importedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    file.transferTo(importedFile);
    String response = userFileService.addFile(importedFile, UserFileType.IMPORTED, jwt);
    importedFile.delete();
    log.info(LogMessages.ENTITY_CREATED, "File", response);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping(value = "/test")
  public ResponseEntity<String> test(@AuthenticationPrincipal Jwt jwt,
      @RequestParam("file") MultipartFile file) throws Exception {
    File importedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    file.transferTo(importedFile);
    log.info(LogMessages.ENTITY_CREATED, "File", null);
    System.out.println("---------------------START--------------------");
    System.out.println("importedFile.getName() = " + importedFile.getName());
    System.out.println("-------------------------------------------------");
    System.out.println("importedFile.getParent() = " + importedFile.getParent());
    System.out.println("-------------------------------------------------");
    System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
    System.out.println("-------------------------------------------------");
    System.out.println("file.getName() = " + file.getName());
    System.out.println("-------------------------------------------------");
    System.out.println(
        "userFileService.getExtension() = " + userFileService.getFileExtension(importedFile));
    System.out.println("---------------------END--------------------");

    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }

  @GetMapping()
  public ResponseEntity<List<FileResponse>> getFiles(@RequestParam("is-active") boolean isActive,
      @AuthenticationPrincipal Jwt jwt) {

    List<FileResponse> files = userFileService.getAllFiles(isActive, jwt);
    log.info(LogMessages.ENTITIES_FOUND, "Files", files);

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/{fileId}")
  public ResponseEntity<ByteArrayResource> getFile(@PathVariable Long fileId,
      @AuthenticationPrincipal Jwt jwt) {

    Map<String, Object> fileData = userFileService.getFile(fileId, jwt);
    log.info(LogMessages.ENTITY_FOUND, "File", fileId);
    byte[] fileBytes = (byte[]) fileData.get("data");  // Hole das byte[] aus den Dateidaten
    ByteArrayResource resource = new ByteArrayResource(fileBytes);
    String fileName = fileData.get("name").toString();
    int length = (int) fileData.get("length");

    return ResponseEntity.status(HttpStatus.OK)
        .contentLength(length)
        .header("Content-type", "application/octet-stream")
        .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
        .body(resource);
  }

}
