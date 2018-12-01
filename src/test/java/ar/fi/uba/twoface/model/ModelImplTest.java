package ar.fi.uba.twoface.model;

import ar.uba.fi.twoface.model.Model;
import ar.uba.fi.twoface.model.ModelImpl;
import ar.uba.fi.twoface.model.TwoFaceException;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ModelImplTest {

    private static final String TEST_BACKEND_LOCATION = "http://localhost:8501/v1/models";

    private static final String TEST_MASK_IMAGE_PATH = "/home/gaston/workspace/datasets/CASIA-WebFace/CASIA-WebFace/data/test/0000045/001.jpg";
    private static final String TEST_REFERENCE_IMAGE_PATH = "/home/gaston/workspace/datasets/CASIA-WebFace/CASIA-WebFace/data/test/0000045/002.jpg";

    Model model = new ModelImpl(TEST_BACKEND_LOCATION);

    @Test
    public void testModel() throws IOException, TwoFaceException {

        BufferedImage maskedImage = ImageIO.read(new File(TEST_MASK_IMAGE_PATH));
        BufferedImage referenceImage = ImageIO.read(new File(TEST_REFERENCE_IMAGE_PATH));

        model.patchImage(maskedImage, referenceImage);
    }

}
