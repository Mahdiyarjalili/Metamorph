package com.metamorph.features.htmltoimage.controller;

import com.metamorph.domains.file.enums.UserFileType;
import com.metamorph.domains.file.service.UserFileService;
import com.metamorph.features.htmltoimage.service.HtmlToImageService;
import com.metamorph.features.htmltopdf.service.HtmlToPdfService;

import java.io.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class HtmlToImageController {

  private final HtmlToImageService htmlToImageService;
  private final HtmlToPdfService htmlToPdfService;
  private final UserFileService userFileService;

  @PostMapping(value = "/convert", params = "action=html-to-image")
  public ResponseEntity<byte[]> convertHtmlToImage(@RequestParam MultipartFile file,
      @AuthenticationPrincipal Jwt jwt)
      throws Exception {

    File inputFile = new File(file.getOriginalFilename());
    file.transferTo(inputFile);
    File outputPdfFile = htmlToPdfService.convertHtmlToPdf(inputFile, jwt);
    File imageFile = htmlToImageService.convertPdfToImage(outputPdfFile);
    userFileService.addFile(imageFile, UserFileType.EXPORTED,jwt);
    byte[] imageFileBytes = htmlToPdfService.getBytes(imageFile);
    String fileName = inputFile.getName();
    inputFile.delete();
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
    headers.setContentType(MediaType.IMAGE_PNG);

    return new ResponseEntity<>(imageFileBytes, headers, HttpStatus.OK);
  }

}