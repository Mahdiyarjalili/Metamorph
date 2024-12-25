package com.metamorph.features.htmltopdf.service;

import com.metamorph.domains.file.enums.FileCategory;
import com.metamorph.domains.file.enums.FileFunction;
import com.metamorph.domains.file.enums.UserFileType;
import com.metamorph.domains.file.service.UserFileService;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.PdfOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Margin;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HtmlToPdfService {

  private static final float POINTS_TO_MM = 0.3528f;
  private static final int A4_HEIGHT_MM = 297;
  private final UserFileService userFileService;

  /*private final KafkaTemplate<String, String> kafkaTemp;

  public HtmlToPdfService(KafkaTemplate<String, String> kafkaTemplate) {

    this.kafkaTemp = kafkaTemplate;
  }*/

  public byte[] getBytes(File file) throws Exception {

    return Files.readAllBytes(file.toPath());

  }


  public File convertHtmlToPdf(File inputHtmlFile, Jwt jwt) throws Exception {
    String message = "HTML to PDF conversion started for file: " + inputHtmlFile.getAbsolutePath();
    //kafkaTemp.send("html-converting-to-Pdf-started", message);
    String inputHtmlContent = getHtmlContentFromFile(inputHtmlFile);

    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch();
      Page page = browser.newPage();
      page.setContent(inputHtmlContent);

      PdfOptions pdfOptions = new PdfOptions().setMargin(new Margin() // Ränder anpassen
          .setTop("0mm").setBottom("0mm").setLeft("0mm").setRight("0mm")).setPrintBackground(true);
      File primitvePdfFile = File.createTempFile("temp_pdf", ".pdf");
      page.pdf(pdfOptions.setPath(primitvePdfFile.toPath()));

      int pageCount = getPdfPageCount(primitvePdfFile);

      String finalHeight = String.valueOf(pageCount * A4_HEIGHT_MM).concat("mm");

      String finalWidth = String.valueOf((int) getPdfPageWidth(primitvePdfFile)).concat("mm");

      PdfOptions newPdfOptions = new PdfOptions().setMargin(new Margin().setTop("0mm").setBottom("0mm").setLeft("0mm").setRight("0mm"))
          .setWidth(finalWidth).setHeight(finalHeight).setPrintBackground(true);

      File finalPdfFile = File.createTempFile("temp_pdf", ".pdf");
      page.pdf(newPdfOptions.setPath(finalPdfFile.toPath()));

      primitvePdfFile.delete();
      browser.close();
      userFileService.addFile(finalPdfFile, FileCategory.DOCUMENT, FileFunction.HTMLTOPDF, UserFileType.EXPORTED, jwt);
      return finalPdfFile;
    }
  }

  private int getPdfPageCount(File pdfFile) throws IOException {
    try (PDDocument document = PDDocument.load(pdfFile)) {
      return document.getNumberOfPages();
    }
  }

  private float getPdfPageWidth(File pdfFile) throws IOException {
    try (PDDocument document = PDDocument.load(pdfFile)) {
      PDPage page = document.getPage(0);
      return page.getMediaBox().getWidth() * POINTS_TO_MM;
    }
  }

  private String getHtmlContentFromFile(File inputHtmlFile) throws IOException {

    return Files.readString(inputHtmlFile.toPath());
  }
}

