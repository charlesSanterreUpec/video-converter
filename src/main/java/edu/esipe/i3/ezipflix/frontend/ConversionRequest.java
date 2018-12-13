package edu.esipe.i3.ezipflix.frontend;

import java.net.URI;

public class ConversionRequest {

    private URI path;

    public ConversionRequest() {
    }
    public ConversionRequest(URI path) {
        this.path = path;
    }
    public URI getPath() {
        return path;
    }
    public void setPath(URI path) {
        this.path = path;
    }
}
