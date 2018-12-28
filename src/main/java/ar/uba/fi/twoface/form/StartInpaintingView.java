package ar.uba.fi.twoface.form;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class StartInpaintingView {

    public String startInpainting() {
        return "view/upload-images?faces-redirect=true";
    }

}
