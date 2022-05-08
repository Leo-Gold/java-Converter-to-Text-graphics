package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class Convert implements TextGraphicsConverter {
    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        double imgRatio = (double) imgWidth / imgHeight;

        int newWidth = 0;
        int newHeight = 0;

        int ratioWidth = imgWidth / width;
        int ratioHeight = imgHeight / height;
        if (imgRatio > maxRatio) {
            throw new BadImageSizeException(imgRatio, maxRatio);
        } else if (ratioWidth >= ratioHeight && ratioWidth >= 1) {
            newWidth = imgWidth / ratioWidth;
            newHeight = imgHeight / ratioWidth;
        } else if (ratioHeight > ratioWidth && ratioHeight >= 1) {
            newWidth = imgWidth / ratioHeight;
            newHeight = imgHeight / ratioHeight;
        }
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);

        WritableRaster bwRaster = bwImg.getRaster();
        StringBuilder result = new StringBuilder();

        for (int h = 0; h < newHeight; h++){
            for (int w = 0; w < newWidth; w++){
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                result.append(c);
                result.append(c);
                result.append(c);
            }
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
