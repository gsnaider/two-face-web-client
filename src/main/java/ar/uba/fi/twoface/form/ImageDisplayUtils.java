package ar.uba.fi.twoface.form;

import org.pmw.tinylog.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

final class ImageDisplayUtils {

    private ImageDisplayUtils() {
    }

    static StreamedContent getImageAsStreamedContent(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            Logger.error("Error generating image for display.", e);
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(outputStream.toByteArray()), "image/jpg");
    }

}
