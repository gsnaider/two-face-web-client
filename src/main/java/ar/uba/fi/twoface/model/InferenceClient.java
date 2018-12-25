package ar.uba.fi.twoface.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.filter.LoggingFilter;
import org.pmw.tinylog.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InferenceClient {

    private static final String INFERENCE_PATH = "two_face_model:predict";

    private final String backendUrl;

    private Client client = ClientBuilder.newClient();

    public InferenceClient(String backendLocation) {
        backendUrl = backendLocation;
    }

    public InferenceResponse infer(InferenceRequest request) throws TwoFaceException {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new TwoFaceException("Error creating request json.", e);
        }
        client.register(new LoggingFilter());
        Response response = client.target(backendUrl)
                .path(INFERENCE_PATH)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(json));

        Logger.info("Inference response status: {}", response.getStatus());

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new TwoFaceException("Remote error on inference.");
        }
        return response.readEntity(InferenceResponse.class);
    }

}
