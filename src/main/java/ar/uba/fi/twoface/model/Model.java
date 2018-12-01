package ar.uba.fi.twoface.model;

import java.awt.image.BufferedImage;

public interface Model {

    BufferedImage patchImage(BufferedImage maskedImage, BufferedImage referenceImage) throws TwoFaceException;

}
