package com.metamorph.features.pdftoimage.service;


import com.metamorph.features.htmltoimage.service.HtmlToImageService;
import com.metamorph.features.htmltopdf.service.HtmlToPdfService;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PdfToImageService {

  private final HtmlToImageService htmlToImageService;
  private final HtmlToPdfService htmlToPdfService;

  public File convertPdfToImage(MultipartFile pdfFile, Jwt jwt) throws Exception {
    File tempPdfFile = File.createTempFile("file", ".pdf");
    pdfFile.transferTo(tempPdfFile);
    return tempPdfFile = htmlToImageService.convertPdfToImage(tempPdfFile,jwt);
  }
}
