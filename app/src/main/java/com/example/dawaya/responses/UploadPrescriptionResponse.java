package com.example.dawaya.responses;

public class UploadPrescriptionResponse {

   URL URL;

    public UploadPrescriptionResponse(com.example.dawaya.responses.URL URL) {
        this.URL = URL;
    }

    public com.example.dawaya.responses.URL getURL() {
        return URL;
    }

    public void setURL(com.example.dawaya.responses.URL URL) {
        this.URL = URL;
    }
}
