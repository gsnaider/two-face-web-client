package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.Model;
import ar.uba.fi.twoface.model.TwoFaceException;
import org.pmw.tinylog.Logger;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.awt.image.BufferedImage;

@ManagedBean
@RequestScoped
public class PatchedImageView {

    private BufferedImage originalImage;
    private BufferedImage patchedImage;
    private BufferedImage referenceImage;

    @PostConstruct
    public void init() {

        Model model = ModelProvider.getModel();

        originalImage = (BufferedImage) SessionManager.get(SessionManager.ORIGINAL_IMAGE_KEY);
        referenceImage = (BufferedImage) SessionManager.get(SessionManager.REFERENCE_IMAGE_KEY);
        BufferedImage maskedImage = (BufferedImage) SessionManager.get(SessionManager.MASKED_IMAGE_KEY);

        try {
            patchedImage =model.patchImage(maskedImage, referenceImage);
        } catch (TwoFaceException e) {
            Logger.error("Error patching image", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error patching image.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public StreamedContent getOriginalImageForDisplay() {
        return ImageDisplayUtils.getImageAsStreamedContent(originalImage);
    }

    public StreamedContent getReferenceImageForDisplay() {
        return ImageDisplayUtils.getImageAsStreamedContent(referenceImage);
    }

    public StreamedContent getPatchedImageForDisplay() {
        return ImageDisplayUtils.getImageAsStreamedContent(patchedImage);
    }

    public String mainMenu() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "../index.xhtml?faces-redirect=true";
    }

    public String back() {
        return "masked?faces-redirect=true";
    }

}
