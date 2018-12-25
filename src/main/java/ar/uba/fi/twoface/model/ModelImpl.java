package ar.uba.fi.twoface.model;

import java.awt.image.BufferedImage;
import java.util.List;

public class ModelImpl implements Model {

    private static final String MASKED_IMAGE_INPUT = "masked_image";
    private static final String REFERENCE_IMAGE_INPUT = "reference_image";

    private static final int IMAGE_SIZE = 128;

    private final InferenceClient inferenceClient;

    public ModelImpl(String backendLocation) {
        inferenceClient = new InferenceClient(backendLocation);
    }


    @Override
    public BufferedImage patchImage(BufferedImage maskedImage, BufferedImage referenceImage) throws TwoFaceException {
        InferenceRequest request = new InferenceRequest();

        //TODO: mask the image.
        //TODO: resize before calling this method.
        request.addInput(MASKED_IMAGE_INPUT, ImageUtils.bufferedImageToPixels(
                ImageUtils.resize(maskedImage, IMAGE_SIZE, IMAGE_SIZE)));
        request.addInput(REFERENCE_IMAGE_INPUT, ImageUtils.bufferedImageToPixels(
                ImageUtils.resize(referenceImage, IMAGE_SIZE, IMAGE_SIZE)));

        InferenceResponse response = inferenceClient.infer(request);

        return ImageUtils.pixelsToBufferedImage(response.getImagePixels());
    }
}
