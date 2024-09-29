package com.metamorph.features.htmltopdf.controller;

import com.metamorph.features.htmltopdf.service.HtmlToPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class HtmlToPdfController {

  private final HtmlToPdfService htmlToPdfService;

  @PostMapping(value = "/convert", params = "action=html-to-pdf")
  public ResponseEntity<byte[]> convertHtmlToPdf(@RequestBody String htmlContent)
      throws Exception {

    byte[] pdfBytes = htmlToPdfService.convertHtmlToPdf(htmlContent);
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf");
    headers.set(HttpHeaders.CONTENT_TYPE, "application/pdf");

    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

  }
}