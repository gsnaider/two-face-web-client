package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.ImageUtils;
import org.pmw.tinylog.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@ManagedBean(name = "uploadImagesView")
@SessionScoped
public class UploadImagesView {

    // TODO move all constants to same place.
    private static final int IMAGE_WIDTH = 128;
    private static final int IMAGE_HEIGTH = 128;

    private BufferedImage imageToModify;
    private BufferedImage referenceImage;

    @PostConstruct
    public void init() {
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
            image = ImageUtils.resize(image, IMAGE_WIDTH, IMAGE_HEIGTH);
            FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (IOException e) {
            Logger.warn("Error uploading image", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error uploading image.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return image;
    }

    public StreamedContent getImageToModifyForDisplay() {
        return getImageAsStreamedContent(imageToModify);
    }

    public StreamedContent getReferenceImageForDisplay() {
        return getImageAsStreamedContent(referenceImage);
    }

    public BufferedImage getImageToModify() {
        return imageToModify;
    }

    public String mask() {
        return "masked?faces-redirect=true";
    }

    StreamedContent getImageAsStreamedContent(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            Logger.error("Error generating image for display.", e);
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(outputStream.toByteArray()), "image/jpg");
    }

    private BufferedImage imagePlaceholder() {
        BufferedImage placeholder =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGTH, BufferedImage.TYPE_INT_RGB);
        Graphics2D graph = placeholder.createGraphics();
        graph.setColor(Color.WHITE);
        graph.fillRect(0, 0, placeholder.getWidth(), placeholder.getHeight());
        graph.setColor(Color.BLACK);
        graph.drawRect(0, 0, placeholder.getWidth() - 1, placeholder.getHeight() - 1);
        graph.dispose();
        return placeholder;
    }

}
