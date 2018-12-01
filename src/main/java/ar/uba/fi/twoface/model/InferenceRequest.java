package ar.uba.fi.twoface.model;

import java.util.HashMap;
import java.util.Map;

public class InferenceRequest {
    
    private Map<String, double[][][][]> inputs;

    public InferenceRequest() {
        this.inputs = new HashMap<>();
    }

    public Map<String, double[][][][]> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, double[][][][]> inputs) {
        this.inputs = inputs;
    }

    public void addInput(String key, double[][][] imagePixels) {
        double[][][][] batch = new double[1][][][];
        batch[0] = imagePixels;
        inputs.put(key, batch);
    }
}
