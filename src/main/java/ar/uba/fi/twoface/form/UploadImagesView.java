package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.Model;
import org.pmw.tinylog.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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

    private BufferedImage imageToModify;
    private BufferedImage referenceImage;

    private Model model;

    @PostConstruct
    public void init() {
        model = ModelProvider.getModel();
        imageToModify = imagePlaceholder();
        referenceImage = imagePlaceholder();
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
            image = model.resize(image, IMAGE_WIDTH, IMAGE_HEIGHT);
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
        return ImageDisplayUtils.getImageAsStreamedContent(imageToModify);
    }

    public StreamedContent getReferenceImageForDisplay() {
        return ImageDisplayUtils.getImageAsStreamedContent(referenceImage);
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

    public String mask() {
        SessionManager.put(SessionManager.ORIGINAL_IMAGE_KEY, imageToModify);
        SessionManager.put(SessionManager.REFERENCE_IMAGE_KEY, referenceImage);
        return "masked?faces-redirect=true";
    }

}
