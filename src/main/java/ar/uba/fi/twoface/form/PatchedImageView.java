package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.Model;
import ar.uba.fi.twoface.model.TwoFaceException;
import org.pmw.tinylog.Logger;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.awt.image.BufferedImage;

@ManagedBean
@RequestScoped
public class PatchedImageView {

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        Model model = ModelProvider.getModel();

        BufferedImage referenceImage = sessionBean.getCroppedReferenceImage();
        BufferedImage maskedImage = sessionBean.getMaskedImage();

        try {
            BufferedImage patchedImage = model.patchImage(maskedImage, referenceImage);
            sessionBean.setPatchedImage(patchedImage);
        } catch (TwoFaceException e) {
            Logger.error("Error patching image", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error patching image.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public StreamedContent getOriginalImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(sessionBean.getCroppedOriginalImage());
    }

    public StreamedContent getReferenceImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(sessionBean.getCroppedReferenceImage());
    }

    public StreamedContent getPatchedImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(sessionBean.getPatchedImage());
    }

    public String mainMenu() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "../index.xhtml?faces-redirect=true";
    }

    public String back() {
        return "masked?faces-redirect=true";
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
}
