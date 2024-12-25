package com.metamorph.features.htmltoimage.service;

import com.metamorph.domains.file.enums.FileCategory;
import com.metamorph.domains.file.enums.FileFunction;
import com.metamorph.domains.file.enums.UserFileType;
import com.metamorph.domains.file.service.UserFileService;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HtmlToImageService {

  private final UserFileService userFileService;

  @KafkaListener(topics = "html-converting-to-Pdf-started", groupId = "group-id", autoStartup = "false")
  public void listen(String message) {
    System.out.println("Received: " + message);
  }

  public File convertPdfToImage(File file, Jwt jwt) throws Exception {
    System.out.println("Received generated pdf for converting to image");
    PDDocument document = PDDocument.load(file);
    PDFRenderer renderer = new PDFRenderer(document);
    int pageCount = document.getNumberOfPages();
    float dpi = 300;
    int width = 0;
    int totalHeight = 0;

    for (int page = 0; page < pageCount; page++) {
      BufferedImage image = renderer.renderImageWithDPI(0, 300);
      width = Math.max(width, image.getWidth());
      totalHeight += image.getHeight();
    }
    BufferedImage combinedImage = new BufferedImage(width, totalHeight, BufferedImage.TYPE_INT_RGB);
    Graphics g = combinedImage.getGraphics();

    int yOffset = 0;
    for (int page = 0; page < pageCount; page++) {
      BufferedImage image = renderer.renderImageWithDPI(page, dpi);
      g.drawImage(image, 0, yOffset, null);
      yOffset += image.getHeight(); // Update den y-Offset
    }
    g.dispose();

    File outputImageFile = new File("output.png");
    ImageIO.write(combinedImage, "png", outputImageFile);
    userFileService.addFile(outputImageFile, FileCategory.IMAGE, FileFunction.PDFTOIMAGE, UserFileType.EXPORTED, jwt);

    document.close();

    return outputImageFile;
  }

}