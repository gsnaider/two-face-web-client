package ar.uba.fi.twoface.model;

public class TwoFaceException extends Exception {

    public TwoFaceException(String s) {
        super(s);
    }

    public TwoFaceException(String s, Exception e) {
        super(s, e);
    }
}
