package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.ImageUtils;
import ar.uba.fi.twoface.model.Model;
import org.pmw.tinylog.Logger;
import org.primefaces.extensions.event.ImageAreaSelectEvent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.awt.*;
import java.awt.image.BufferedImage;

import static ar.uba.fi.twoface.model.Constants.*;

@ManagedBean
@SessionScoped
public class FaceSelectionView {

    private Model model;

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    private int originalX;
    private int originalY;
    private int originalWidth;
    private int originalHeight;

    private int referenceX;
    private int referenceY;
    private int referenceWidth;
    private int referenceHeight;

    @PostConstruct
    public void init() {
        model = ModelProvider.getModel();

        originalX = 0;
        originalY = 0;
        originalWidth = sessionBean.getOriginalImage().getWidth();
        originalHeight = sessionBean.getOriginalImage().getHeight();

        referenceX = 0;
        referenceY = 0;
        referenceWidth = sessionBean.getReferenceImage().getWidth();
        referenceHeight = sessionBean.getReferenceImage().getHeight();
    }

    public void originalSelectListener(final ImageAreaSelectEvent e) {
        originalX = e.getX1();
        originalY = e.getY1();
        originalWidth = e.getWidth();
        originalHeight = e.getHeight();
        showSelectionMessage(e);
    }

    public void referenceSelectListener(final ImageAreaSelectEvent e) {
        referenceX = e.getX1();
        referenceY = e.getY1();
        referenceWidth = e.getWidth();
        referenceHeight = e.getHeight();
        showSelectionMessage(e);
    }

    private void showSelectionMessage(final ImageAreaSelectEvent e) {
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selección",
                "X: " + e.getX1()
                        + ", Y: " + e.getY1()
                        + ", Ancho: " + e.getWidth()
                        + ", Alto: " + e.getHeight());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public StreamedContent getOriginalImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(sessionBean.getOriginalImage());
    }

    public StreamedContent getReferenceImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(sessionBean.getReferenceImage());
    }

    public String next() {

        Logger.info("originalX {}", originalX);
        Logger.info("originalY {}", originalY);
        Logger.info("originalWidth {}", originalWidth);
        Logger.info("originalHeight {}", originalHeight);
        Logger.info("Original image ");

        Logger.info("referenceX {}", referenceX);
        Logger.info("referenceY {}", referenceY);
        Logger.info("referenceWidth {}", referenceWidth);
        Logger.info("referenceHeight {}", referenceHeight);

        BufferedImage croppedOriginalImage =
                model.crop(
                        sessionBean.getOriginalImage(),
                        originalX,
                        originalY,
                        originalWidth,
                        originalHeight);
        BufferedImage croppedReferenceImage =
                model.crop(
                        sessionBean.getReferenceImage(),
                        referenceX,
                        referenceY,
                        referenceWidth,
                        referenceHeight);

        croppedOriginalImage =
                model.resize(croppedOriginalImage, IMAGE_WIDTH, IMAGE_HEIGHT);
        croppedReferenceImage =
                model.resize(croppedReferenceImage, IMAGE_WIDTH, IMAGE_HEIGHT);

        sessionBean.setCroppedOriginalImage(croppedOriginalImage);
        sessionBean.setCroppedReferenceImage(croppedReferenceImage);

        // TODO before redirecting, clear up the values of X, Y, width, height,
        // so that if the user goes back, the selection is cleared.
        return "masked?faces-redirect=true";
    }

    public String back() {
        return "upload-images?faces-redirect=true";
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
}
