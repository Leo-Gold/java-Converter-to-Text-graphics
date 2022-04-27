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
        if (imgRatio > maxRatio) {
            throw new BadImageSizeException(imgRatio, maxRatio);
        } else if ((width == height && imgWidth > imgHeight) || width / imgRatio <= height) {
            newWidth = width;
            newHeight = (int) (width / imgRatio);
        } else if ((width == height && imgHeight > imgWidth) || height / imgRatio <= width ) {
            newHeight = height;
            newWidth = (int) (height / imgRatio);
        } else if (height == width) {
            newHeight = height;
            newWidth = width;
        }
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);

        WritableRaster bwRaster = bwImg.getRaster();

        char[][] arrChar = new char[newHeight][newWidth];
        for (int h = 0; h < newHeight; h++){
            for (int w = 0; w < newWidth; w++){
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                arrChar[h][w] = c;
            }
        }

        StringBuilder result = new StringBuilder();

        for (char[] rows : arrChar) {
            for (char px : rows) {
                result.append(px);
                result.append(px);
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
