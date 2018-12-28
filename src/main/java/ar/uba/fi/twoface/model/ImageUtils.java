package ar.uba.fi.twoface.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        Image tmp = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }

    public static double[][][] bufferedImageToPixels(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        double[][][] normalizedPixels = new double[width][height][];

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                int rgb = image.getRGB(col, row);
                double[] normalizedPixel = normalizePixel(rgb);
                normalizedPixels[col][row] = normalizedPixel;
            }
        }

        return normalizedPixels;
    }

    public static BufferedImage pixelsToBufferedImage(double[][][] pixels) {
        int width = pixels.length;
        int height = pixels[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                image.setRGB(col, row, getRGB(pixels[col][row]));
            }
        }
        return image;
    }

    /**
     * Splits into 3 values (RGB) and normalizes to range [0,1]
     */
    public static double[] normalizePixel(int rgb) {

        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;

        double[] normalizedPixel = new double[3];

        normalizedPixel[0] = r / 255.0;
        normalizedPixel[1] = g / 255.0;
        normalizedPixel[2] = b / 255.0;

        return normalizedPixel;
    }

    private static int getRGB(double[] pixel) {
        int r = (int) (pixel[0] * 255.0);
        int g = (int) (pixel[1] * 255.0);
        int b = (int) (pixel[2] * 255.0);
        int rgb = r << 16 | g << 8 | b;
        return rgb;
    }

    /**
     * Modifies the given image, setting a mask of zeroes on the given mask.
     */
    public static void maskImage(BufferedImage image, Rectangle mask) {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fill(mask);
        g2d.dispose();
    }
}
