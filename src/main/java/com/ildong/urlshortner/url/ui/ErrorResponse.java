package com.ildong.urlshortner.url.ui;

public class ErrorResponse {
    String code;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
