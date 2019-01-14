package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.Model;
import org.pmw.tinylog.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static ar.uba.fi.twoface.model.Constants.IMAGE_HEIGHT;
import static ar.uba.fi.twoface.model.Constants.IMAGE_WIDTH;

@ManagedBean(name = "uploadImagesView")
@SessionScoped
public class UploadImagesView {

    private boolean imageToModifyUploaded = false;
    private boolean referenceImageUploaded = false;

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        sessionBean.setOriginalImage(imagePlaceholder());
        sessionBean.setReferenceImage(imagePlaceholder());
    }

    public void uploadImageToModify(FileUploadEvent event) {
        BufferedImage imageToModify = getImageFromFileUploadEvent(event);
        sessionBean.setOriginalImage(imageToModify);
        imageToModifyUploaded = imageToModify != null;

    }

    public void uploadReferenceImage(FileUploadEvent event) {
        BufferedImage referenceImage = getImageFromFileUploadEvent(event);
        sessionBean.setReferenceImage(referenceImage);
        referenceImageUploaded = referenceImage != null;
    }

    private BufferedImage getImageFromFileUploadEvent(FileUploadEvent event) {
        byte[] contents = event.getFile().getContents();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(contents));
            FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (IOException e) {
            Logger.error("Error uploading image", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error uploading image.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return image;
    }

    public StreamedContent getImageToModifyForDisplay() {
        return ImageDisplayUtils.getImageAsStreamedContent(sessionBean.getOriginalImage());
    }

    public StreamedContent getReferenceImageForDisplay() {
        return ImageDisplayUtils.getImageAsStreamedContent(sessionBean.getReferenceImage());
    }

    private BufferedImage imagePlaceholder() {
        BufferedImage placeholder =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graph = placeholder.createGraphics();
        graph.setColor(Color.WHITE);
        graph.fillRect(0, 0, placeholder.getWidth(), placeholder.getHeight());
        graph.setColor(Color.BLACK);
        graph.drawRect(0, 0, placeholder.getWidth() - 1, placeholder.getHeight() - 1);
        graph.dispose();
        return placeholder;
    }

    public String next() {
        return "face-selection?faces-redirect=true";
    }

    public String back() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "../index.xhtml?faces-redirect=true";
    }

    public boolean getImageToModifyUploaded() {
        return imageToModifyUploaded;
    }

    public boolean getReferenceImageUploaded() {
        return referenceImageUploaded;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
}
