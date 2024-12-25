package com.metamorph.features.pdftoimage.controller;

import com.metamorph.features.htmltoimage.service.HtmlToImageService;
import com.metamorph.features.htmltopdf.service.HtmlToPdfService;
import com.metamorph.features.pdftoimage.service.PdfToImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class PdfToImageController {

  private final HtmlToPdfService htmlToPdfService;
  private final PdfToImageService pdfToImageService;

  @PostMapping(value = "/convert", params = "action=pdf-to-image")
  public ResponseEntity<byte[]> convertPdfToImage(@RequestParam MultipartFile file,@AuthenticationPrincipal Jwt jwt)
      throws Exception {
    byte[] imageFileBytes = htmlToPdfService.getBytes(pdfToImageService.convertPdfToImage(file,jwt));
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=" + file.getOriginalFilename());
    headers.setContentType(MediaType.IMAGE_PNG);
    return new ResponseEntity<>(imageFileBytes, headers, HttpStatus.OK);
  }

}
