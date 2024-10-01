package com.metamorph.domains.importedfile.controller;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import com.metamorph.domains.importedfile.dto.FileResponse;
import com.metamorph.domains.importedfile.service.FileService;
import com.metamorph.util.LogMessages;
import java.util.List;
import java.util.Map;
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

  private final FileService fileService;

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createFile(@AuthenticationPrincipal Jwt jwt,
      @RequestParam("file") MultipartFile file,
      @RequestParam("type") String type) {

    String response = fileService.addFile(file, type, jwt);
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
  public ResponseEntity<ByteArrayResource> getFile(@PathVariable Long fileId,
      @AuthenticationPrincipal Jwt jwt) {

    Map<String, Object> fileData = fileService.getFile(fileId, jwt);
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
