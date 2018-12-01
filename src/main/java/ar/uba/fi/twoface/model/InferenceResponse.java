package ar.uba.fi.twoface.model;

import java.util.List;

public class InferenceResponse {

    private double[][][][] outputs;

    public double[][][][] getOutputs() {
        return outputs;
    }

    public void setOutputs(double[][][][] outputs) {
        this.outputs = outputs;
    }

    public double[][][] getImagePixels() {
        return outputs[0];
    }
}
