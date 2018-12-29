package ar.uba.fi.twoface.form;

import ar.uba.fi.twoface.model.Model;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

final class ModelProvider {

    private ModelProvider() {}

    static Model getModel() {
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();
        return (Model) servletContext.getAttribute(Model.CTX_ATTRIBUTE);
    }

}
