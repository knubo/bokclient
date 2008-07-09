package no.knubo.bok.client.misc;

public interface ServerResponseWithErrorFeedback extends ServerResponse {

    public void onError();
}
