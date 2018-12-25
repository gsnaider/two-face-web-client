package ar.uba.fi.twoface.form;

import org.pmw.tinylog.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@ManagedBean
@SessionScoped
public class UploadImagesView {

    private UploadedFile imageToModifyFile;
    private UploadedFile referenceImageFile;

    private BufferedImage imageToModify;
    private BufferedImage referenceImage;

    @PostConstruct
    public void init() {
        imageToModify = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
        referenceImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);

    }

    public void uploadImageToModify(FileUploadEvent event) {
        imageToModify = getImageFromFileUploadEvent(event);
    }

    public void uploadReferenceImage(FileUploadEvent event) {
        referenceImage = getImageFromFileUploadEvent(event);
    }

    private BufferedImage getImageFromFileUploadEvent(FileUploadEvent event) {
        byte[] contents = event.getFile().getContents();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(contents));
            FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (IOException e) {
            Logger.warn("Error uploading image", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error uploading image.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return image;
    }

    public UploadedFile getImageToModifyFile() {
        return imageToModifyFile;
    }

    public void setImageToModifyFile(UploadedFile imageToModifyFile) {
        this.imageToModifyFile = imageToModifyFile;
    }

    public UploadedFile getReferenceImageFile() {
        return referenceImageFile;
    }

    public void setReferenceImageFile(UploadedFile referenceImageFile) {
        this.referenceImageFile = referenceImageFile;
    }

    public byte[] getImageToModify() {
        return getImageAsByteArray(imageToModify);
    }

    public byte[] getReferenceImage() {
        return getImageAsByteArray(referenceImage);
    }

    private byte[] getImageAsByteArray(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            Logger.error("Error generating image to render.", e);
        }
        return outputStream.toByteArray();
    }

}
