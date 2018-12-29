package ar.uba.fi.twoface.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ModelImpl implements Model {

    private static final String MASKED_IMAGE_INPUT = "masked_image";
    private static final String REFERENCE_IMAGE_INPUT = "reference_image";

    private final InferenceClient inferenceClient;

    public ModelImpl(String backendLocation) {
        inferenceClient = new InferenceClient(backendLocation);
    }


    @Override
    public void maskImage(BufferedImage image, Rectangle mask) {
        ImageUtils.maskImage(image, mask);
    }

    @Override
    public BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        return ImageUtils.resize(image, newWidth, newHeight);
    }

    @Override
    public BufferedImage patchImage(BufferedImage maskedImage, BufferedImage referenceImage) throws TwoFaceException {
        InferenceRequest request = new InferenceRequest();
        request.addInput(MASKED_IMAGE_INPUT, ImageUtils.bufferedImageToPixels(maskedImage));
        request.addInput(REFERENCE_IMAGE_INPUT, ImageUtils.bufferedImageToPixels(referenceImage));

        InferenceResponse response = inferenceClient.infer(request);

        return ImageUtils.pixelsToBufferedImage(response.getImagePixels());
    }
}
