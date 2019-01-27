package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.Model;
import org.pmw.tinylog.Logger;
import org.primefaces.extensions.event.ImageAreaSelectEvent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.awt.image.BufferedImage;

import static ar.uba.fi.twoface.model.Constants.IMAGE_HEIGHT;
import static ar.uba.fi.twoface.model.Constants.IMAGE_WIDTH;

@ManagedBean
@SessionScoped
public class FaceSelectionView {

    private static final int MIN_SELECTION_SIZE = 50;

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

    private boolean originalSelected;
    private boolean referenceSelected;

    @PostConstruct
    public void init() {
        model = ModelProvider.getModel();
        setDefaultValues();
    }

    private void setDefaultValues() {
        originalSelected = false;
        referenceSelected = false;
    }

    public void originalSelectListener(final ImageAreaSelectEvent e) {
        if (e.getWidth() < MIN_SELECTION_SIZE || e.getHeight() < MIN_SELECTION_SIZE) {
            showSmallRegionErrorMessage();
            originalSelected = false;
        } else {
            originalX = e.getX1();
            originalY = e.getY1();
            originalWidth = e.getWidth();
            originalHeight = e.getHeight();
            showSelectionMessage(e);
            originalSelected = true;
        }
    }

    public void referenceSelectListener(final ImageAreaSelectEvent e) {
        if (e.getWidth() < MIN_SELECTION_SIZE || e.getHeight() < MIN_SELECTION_SIZE) {
            showSmallRegionErrorMessage();
            referenceSelected = false;
        } else {
            referenceX = e.getX1();
            referenceY = e.getY1();
            referenceWidth = e.getWidth();
            referenceHeight = e.getHeight();
            showSelectionMessage(e);
            referenceSelected = true;
        }
    }

    private void showSmallRegionErrorMessage() {
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selección inválida",
                "Debe seleccionar una región más grande.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void showSelectionMessage(final ImageAreaSelectEvent e) {
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selección",
                "X: " + e.getX1()
                        + ", Y: " + e.getY1()
                        + ", Ancho: " + e.getWidth()
                        + ", Alto: " + e.getHeight());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String next() {
        Logger.info("originalX {}", originalX);
        Logger.info("originalY {}", originalY);
        Logger.info("originalWidth {}", originalWidth);
        Logger.info("originalHeight {}", originalHeight);

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
                sessionBean.getUseReferenceImage() ?
                        model.crop(
                                sessionBean.getReferenceImage(),
                                referenceX,
                                referenceY,
                                referenceWidth,
                                referenceHeight) :
                        sessionBean.getReferenceImage();

        croppedOriginalImage =
                model.resize(croppedOriginalImage, IMAGE_WIDTH, IMAGE_HEIGHT);
        croppedReferenceImage =
                model.resize(croppedReferenceImage, IMAGE_WIDTH, IMAGE_HEIGHT);

        sessionBean.setCroppedOriginalImage(croppedOriginalImage);
        sessionBean.setCroppedReferenceImage(croppedReferenceImage);

        setDefaultValues();
        return "masked?faces-redirect=true";
    }

    public String back() {
        setDefaultValues();
        return "upload-images?faces-redirect=true";
    }

    public StreamedContent getOriginalImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(sessionBean.getOriginalImage());
    }

    public StreamedContent getReferenceImageForDisplay() {
        return ImageDisplayUtils
                .getImageAsStreamedContent(sessionBean.getReferenceImage());
    }

    public boolean getOriginalSelected() {
        return originalSelected;
    }

    public boolean getReferenceSelected() {
        return referenceSelected;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
}
