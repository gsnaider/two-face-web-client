package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.ImageUtils;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.awt.*;
import java.awt.image.BufferedImage;

@ManagedBean
@SessionScoped
public class MaskedImageView {

    private static final int IMAGE_WIDTH = 128;
    private static final int IMAGE_HEIGHT = 128;
    private static final int PATCH_WIDTH = 32;
    private static final int PATCH_HEIGHT = 32;

    @ManagedProperty(value = "#{uploadImagesView}")
    private UploadImagesView uploadImagesView;

    public StreamedContent getOriginalImageForDisplay() {
        return uploadImagesView.getImageToModifyForDisplay();
    }

    public StreamedContent getReferenceImageForDisplay() {
        return uploadImagesView.getReferenceImageForDisplay();
    }

    public StreamedContent getMaskedImageForDisplay() {
        // TODO maybe make the image copy within ImageUtils.maskImage.
        BufferedImage imageToMask = ImageUtils.copy(uploadImagesView.getImageToModify());
        ImageUtils.maskImage(imageToMask, getMask());
        return uploadImagesView.getImageAsStreamedContent(imageToMask);
    }

    // TODO move to own class.
    private static Rectangle getMask() {
        int maskUpperLeftCol = (IMAGE_WIDTH - PATCH_WIDTH) / 2;
        int maskUpperLeftRow = (IMAGE_HEIGHT - PATCH_HEIGHT) / 2;
        return new Rectangle(maskUpperLeftCol, maskUpperLeftRow, PATCH_WIDTH, PATCH_HEIGHT);
    }

    public void setUploadImagesView(UploadImagesView uploadImagesView) {
        this.uploadImagesView = uploadImagesView;
    }
}
