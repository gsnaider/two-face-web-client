package ar.uba.fi.twoface.model;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TwoFaceException extends Exception {

    public TwoFaceException(String s) {
        super(s);
    }

    public TwoFaceException(String s, Exception e) {
        super(s, e);
    }

}
