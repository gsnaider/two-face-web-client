package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.ImageUtils;
import ar.uba.fi.twoface.model.Model;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.awt.*;
import java.awt.image.BufferedImage;

import static ar.uba.fi.twoface.model.Constants.IMAGE_HEIGHT;
import static ar.uba.fi.twoface.model.Constants.IMAGE_WIDTH;
import static ar.uba.fi.twoface.model.Constants.PATCH_HEIGHT;
import static ar.uba.fi.twoface.model.Constants.PATCH_WIDTH;

@ManagedBean
@SessionScoped
public class MaskedImageView {

    private BufferedImage maskedImage;

    private Model model;

    @PostConstruct
    public void init() {
        model = ModelProvider.getModel();
    }

    public StreamedContent getOriginalImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(
                        (BufferedImage) SessionManager.get(SessionManager.ORIGINAL_IMAGE_KEY));
    }

    public StreamedContent getReferenceImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(
                        (BufferedImage) SessionManager.get(SessionManager.REFERENCE_IMAGE_KEY));
    }

    // TODO see if the mask should be done in another place (not the get method for display).
    public StreamedContent getMaskedImageForDisplay() {
        BufferedImage originalImage =
                (BufferedImage) SessionManager.get(SessionManager.ORIGINAL_IMAGE_KEY);

        // TODO maybe make the image copy within Model#maskImage.
        maskedImage = ImageUtils.copy(originalImage);
        model.maskImage(maskedImage, getMask());

        return ImageDisplayUtils.getImageAsStreamedContent(maskedImage);
    }

    // TODO move to own class.
    private static Rectangle getMask() {
        int maskUpperLeftCol = (IMAGE_WIDTH - PATCH_WIDTH) / 2;
        int maskUpperLeftRow = (IMAGE_HEIGHT - PATCH_HEIGHT) / 2;
        return new Rectangle(maskUpperLeftCol, maskUpperLeftRow, PATCH_WIDTH, PATCH_HEIGHT);
    }

    public String patch() {
        SessionManager.put(SessionManager.MASKED_IMAGE_KEY, maskedImage);
        return "patched?faces-redirect=true";
    }
}
