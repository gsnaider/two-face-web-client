package ar.uba.fi.twoface.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Model {

    static final String CTX_ATTRIBUTE = "model";

    void maskImage(BufferedImage image, Rectangle mask);

    /**
     * Returns a {@link BufferedImage} which is the result of cropping the given
     * {@code image} on a rectangular region.
     *
     * @param x      the X coordinate of the upper-left corner of the specified
     *               rectangular region
     * @param y      the Y coordinate of the upper-left corner of the specified
     *               rectangular region
     * @param width  the width of the specified rectangular region
     * @param height the height of the specified rectangular region
     */
    BufferedImage crop(BufferedImage image, int x, int y, int width, int height);

    BufferedImage resize(BufferedImage image, int newWidth, int newHeight);

    BufferedImage patchImage(BufferedImage maskedImage, BufferedImage referenceImage) throws TwoFaceException;

}
