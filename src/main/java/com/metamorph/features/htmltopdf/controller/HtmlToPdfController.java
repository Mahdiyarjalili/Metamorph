package com.metamorph.features.htmltopdf.controller;

import com.metamorph.features.htmltopdf.service.HtmlToPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class HtmlToPdfController {

  private final HtmlToPdfService htmlToPdfService;

  @PostMapping(value = "/convert", params = "action=html-to-pdf")
  public ResponseEntity<byte[]> convertHtmlToPdf(@RequestBody String htmlContent,@AuthenticationPrincipal Jwt jwt)
      throws Exception {

    byte[] pdfBytes = htmlToPdfService.getBytes(htmlToPdfService.convertHtmlToPdf(htmlContent, jwt));
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf");
    headers.set(HttpHeaders.CONTENT_TYPE, "application/pdf");

    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

  }
}