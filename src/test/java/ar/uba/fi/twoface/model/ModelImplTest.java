package ar.uba.fi.twoface.model;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ar.uba.fi.twoface.model.Constants.IMAGE_HEIGHT;
import static ar.uba.fi.twoface.model.Constants.IMAGE_WIDTH;
import static ar.uba.fi.twoface.model.Constants.PATCH_HEIGHT;
import static ar.uba.fi.twoface.model.Constants.PATCH_WIDTH;

public class ModelImplTest {

    private static final String TEST_BACKEND_LOCATION = "http://localhost:8501/v1/models";

    private static final String TEST_MASK_IMAGE_PATH = "src/test/resources/original.jpg";
    private static final String TEST_REFERENCE_IMAGE_PATH = "src/test/resources/reference.jpg";

    private Model model = new ModelImpl(TEST_BACKEND_LOCATION);

    @Test
    public void testModel() throws IOException, TwoFaceException {

        BufferedImage maskedImage = ImageIO.read(new File(TEST_MASK_IMAGE_PATH));
        BufferedImage referenceImage = ImageIO.read(new File(TEST_REFERENCE_IMAGE_PATH));

        maskedImage = model.resize(maskedImage, IMAGE_WIDTH, IMAGE_HEIGHT);
        referenceImage = model.resize(referenceImage, IMAGE_WIDTH, IMAGE_HEIGHT);

        int maskUpperLeftCol = (IMAGE_WIDTH - PATCH_WIDTH) / 2;
        int maskUpperLeftRow = (IMAGE_HEIGHT - PATCH_HEIGHT) / 2;

        model.maskImage(maskedImage, new Rectangle(maskUpperLeftCol, maskUpperLeftRow, PATCH_WIDTH, PATCH_HEIGHT));
        File maskedFile = new File("src/test/results/masked.jpg");
        maskedFile.getParentFile().mkdirs();
        ImageIO.write(maskedImage, "jpg", maskedFile);

        BufferedImage response = model.patchImage(maskedImage, referenceImage);
        File responseFile = new File("src/test/results/patched.jpg");
        responseFile.getParentFile().mkdirs();
        ImageIO.write(response, "jpg", responseFile);
    }

}
