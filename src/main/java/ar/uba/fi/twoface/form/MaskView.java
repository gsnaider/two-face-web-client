package ar.uba.fi.twoface.form;

import org.primefaces.extensions.event.ImageAreaSelectEvent;
import org.primefaces.extensions.event.ResizeEvent;
import org.primefaces.extensions.event.RotateEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class MaskView {

    // TODO mask sizes are based on the size of the display image, not the size of the BufferedImage.
    // See how to fix this.

    public void selectEndListener(final ImageAreaSelectEvent e) {
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Area selected",
                "X1: " + e.getX1()
                        + ", X2: " + e.getX2()
                        + ", Y1: " + e.getY1()
                        + ", Y2: " + e.getY2()
                        + ", Image width: " + e.getImgWidth()
                        + ", Image height: " + e.getImgHeight());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void rotateListener(final RotateEvent e) {
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image rotated",
                "Degree:" + e.getDegree());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void resizeListener(final ResizeEvent e) {
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image resized",
                "Width:" + e.getWidth() + ", Height: " + e.getHeight());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
    