package com.metamorph.features.htmltopdf.controller;

import com.metamorph.domains.file.service.UserFileService;
import com.metamorph.features.htmltopdf.service.HtmlToPdfService;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class HtmlToPdfController {

  private final HtmlToPdfService htmlToPdfService;
  private final UserFileService userFileService;

  @PostMapping(value = "/convert", params = "action=html-to-pdf")
  public ResponseEntity<byte[]> convertHtmlToPdf(@RequestBody MultipartFile htmlMultipartFile, @AuthenticationPrincipal Jwt jwt)
      throws Exception {
    File htmlFile = userFileService.convertMultipartFileToFile(htmlMultipartFile);
    byte[] pdfBytes = htmlToPdfService.getBytes(htmlToPdfService.convertHtmlToPdf(htmlFile, jwt));
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf");
    headers.set(HttpHeaders.CONTENT_TYPE, "application/pdf");

    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

  }
}