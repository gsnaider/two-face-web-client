package ar.uba.fi.twoface.model;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ModelImplTest {

    private static final int IMAGE_SIZE = 128;
    private static final int PATCH_SIZE = 32;

    private static final String TEST_BACKEND_LOCATION = "http://localhost:8501/v1/models";

    private static final String TEST_MASK_IMAGE_PATH = "/home/gaston/workspace/datasets/CASIA-WebFace/CASIA-WebFace/data/test/0000045/001.jpg";
    private static final String TEST_REFERENCE_IMAGE_PATH = "/home/gaston/workspace/datasets/CASIA-WebFace/CASIA-WebFace/data/test/0000045/002.jpg";

    Model model = new ModelImpl(TEST_BACKEND_LOCATION);

    @Test
    public void testModel() throws IOException, TwoFaceException {

        BufferedImage maskedImage = ImageIO.read(new File(TEST_MASK_IMAGE_PATH));
        BufferedImage referenceImage = ImageIO.read(new File(TEST_REFERENCE_IMAGE_PATH));

        maskedImage = model.resize(maskedImage, IMAGE_SIZE, IMAGE_SIZE);
        referenceImage = model.resize(referenceImage, IMAGE_SIZE, IMAGE_SIZE);

        int maskUpperLeftCol = (IMAGE_SIZE - PATCH_SIZE) / 2;
        int maskUpperLeftRow = (IMAGE_SIZE - PATCH_SIZE) / 2;

        model.maskImage(maskedImage, new Rectangle(maskUpperLeftCol, maskUpperLeftRow, PATCH_SIZE, PATCH_SIZE));
        ImageIO.write(maskedImage, "jpg", new File("masked.jpg"));

        BufferedImage response = model.patchImage(maskedImage, referenceImage);
        File outputfile = new File("image.jpg");
        ImageIO.write(response, "jpg", outputfile);
    }

}
