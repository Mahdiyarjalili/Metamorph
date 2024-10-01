package com.metamorph.features.htmltoimage.controller;

import com.metamorph.features.htmltoimage.service.HtmlToImageService;
import com.metamorph.features.htmltopdf.service.HtmlToPdfService;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class HtmlToImageController {

  private final HtmlToImageService htmlToImageService;
  private final HtmlToPdfService htmlToPdfService;

  @PostMapping(value = "/convert", params = "action=html-to-image")
  public ResponseEntity<byte[]> convertHtmlToImage (@RequestBody String htmlContent, @AuthenticationPrincipal Jwt jwt)
      throws Exception {

    File file = htmlToPdfService.convertHtmlToPdf(htmlContent, jwt);
    byte[] image = htmlToImageService.convertHtmlToImage(file);
    System.out.println("image bytes: " + image.toString());
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=html-to-image");
    headers.setContentType(MediaType.IMAGE_PNG);

    return new ResponseEntity<>(image,headers, HttpStatus.OK);
  }

}