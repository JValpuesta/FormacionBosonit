package com.bosonit.block6pathvariableheaders;

import java.util.List;

public class Respuesta {
    private String body;
    private List<String> headers;
    private List<String> requestParams;

    public Respuesta(String body, List<String> requestParams, List<String> headers) {
        this.body = body;
        this.requestParams = requestParams;
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<String> requestParams) {
        this.requestParams = requestParams;
    }
}

