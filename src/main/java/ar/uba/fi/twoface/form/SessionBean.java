package ar.uba.fi.twoface.form;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.awt.image.BufferedImage;

@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean {

    private BufferedImage originalImage;
    private BufferedImage referenceImage;
    private BufferedImage maskedImage;
    private BufferedImage patchedImage;

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }

    public BufferedImage getReferenceImage() {
        return referenceImage;
    }

    public void setReferenceImage(BufferedImage referenceImage) {
        this.referenceImage = referenceImage;
    }

    public BufferedImage getMaskedImage() {
        return maskedImage;
    }

    public void setMaskedImage(BufferedImage maskedImage) {
        this.maskedImage = maskedImage;
    }

    public BufferedImage getPatchedImage() {
        return patchedImage;
    }

    public void setPatchedImage(BufferedImage patchedImage) {
        this.patchedImage = patchedImage;
    }
}
