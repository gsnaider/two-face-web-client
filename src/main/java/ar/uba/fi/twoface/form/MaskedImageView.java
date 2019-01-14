package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.ImageUtils;
import ar.uba.fi.twoface.model.Model;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.awt.*;
import java.awt.image.BufferedImage;

import static ar.uba.fi.twoface.model.Constants.*;

@ManagedBean
@RequestScoped
public class MaskedImageView {

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        Model model = ModelProvider.getModel();

        BufferedImage originalImage = sessionBean.getCroppedOriginalImage();
        BufferedImage maskedImage = ImageUtils.copy(originalImage);
        model.maskImage(maskedImage, getMask());

        sessionBean.setMaskedImage(maskedImage);
    }

    public StreamedContent getOriginalImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(sessionBean.getCroppedOriginalImage());
    }

    public StreamedContent getReferenceImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(sessionBean.getCroppedReferenceImage());
    }

    // TODO see if the mask should be done in another place (not the get method for display).
    public StreamedContent getMaskedImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(sessionBean.getMaskedImage());
    }

    private static Rectangle getMask() {
        int maskUpperLeftCol = (IMAGE_WIDTH - PATCH_WIDTH) / 2;
        int maskUpperLeftRow = (IMAGE_HEIGHT - PATCH_HEIGHT) / 2;
        return new Rectangle(maskUpperLeftCol, maskUpperLeftRow, PATCH_WIDTH, PATCH_HEIGHT);
    }

    public String next() {
        return "patched?faces-redirect=true";
    }

    public String back() {
        return "face-selection?faces-redirect=true";
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
}
