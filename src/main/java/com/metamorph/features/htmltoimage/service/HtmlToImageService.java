package com.metamorph.features.htmltoimage.service;

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

  public byte[] convertHtmlToImage(File file) throws Exception {

    PDDocument document = PDDocument.load(file);
    PDFRenderer renderer = new PDFRenderer(document);

    BufferedImage image = renderer.renderImageWithDPI(0,300);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, "png", baos);

    byte[] imageBytes = baos.toByteArray();

    document.close();

    return imageBytes;

  }

}