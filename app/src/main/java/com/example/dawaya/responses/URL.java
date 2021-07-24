package com.example.dawaya.responses;

public class URL {
    Object headers;
    String body;
    int statusCodeValue;
    String statusCode;

    public URL(Object headers, String body, int statusCodeValue, String statusCode) {
        this.headers = headers;
        this.body = body;
        this.statusCodeValue = statusCodeValue;
        this.statusCode = statusCode;
    }

    public Object getHeaders() {
        return headers;
    }
    public void setHeaders(Object headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCodeValue() {
        return statusCodeValue;
    }
    public void setStatusCodeValue(int statusCodeValue) {
        this.statusCodeValue = statusCodeValue;
    }

    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
