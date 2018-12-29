package ar.uba.fi.twoface.form;

import javax.faces.context.FacesContext;
import java.util.Map;

final class SessionManager {

    static final String ORIGINAL_IMAGE_KEY = "originalImage";
    static final String REFERENCE_IMAGE_KEY = "referenceImage";
    static final String MASKED_IMAGE_KEY = "maskedImage";

    private SessionManager() {

    }

    static void put(String s, Object o) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put(s, o);
    }

    static Object get(String s) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        return sessionMap.get(s);
    }



}
