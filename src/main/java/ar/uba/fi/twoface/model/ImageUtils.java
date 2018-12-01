package ar.uba.fi.twoface.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static double[][][] bufferedImageToPixels(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][][] normalizedPixels = new double[height][width][];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int rgb = image.getRGB(col, row);
                double[] normalizedPixel = normalizePixel(rgb);
                normalizedPixels[row][col] = normalizedPixel;
            }
        }

        return normalizedPixels;
    }

    public static BufferedImage pixelsToBufferedImage(double[][][] pixels) {
        //TODO
        return null;
    }

    /**
     * Splits into 3 values (RGB) and normalizes to range [0,1]
     */
    public static double[] normalizePixel(int rgb) {

        int r = (rgb>>16) & 0xff;
        int g = (rgb>>8) & 0xff;
        int b = rgb & 0xff;

        double[] normalizedPixel = new double[3];

        normalizedPixel[0] = r / 255.0;
        normalizedPixel[1] = g / 255.0;
        normalizedPixel[2] = b / 255.0;

        return normalizedPixel;
    }

    private static int[][] getRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getRGB(col, row);
            }
        }

        return result;
    }

}
