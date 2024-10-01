package com.metamorph.features.htmltoimage.service;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.*;

@Service
public class HtmlToImageService {

  public File convertPdfToImage(File file) throws Exception {

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

    document.close();

    return outputImageFile;
  }

}