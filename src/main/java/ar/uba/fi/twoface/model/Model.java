package ar.uba.fi.twoface.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Model {

    void maskImage(BufferedImage image, Rectangle mask);

    BufferedImage resize(BufferedImage image, int newWidth, int newHeight);

    BufferedImage patchImage(BufferedImage maskedImage, BufferedImage referenceImage) throws TwoFaceException;

}
