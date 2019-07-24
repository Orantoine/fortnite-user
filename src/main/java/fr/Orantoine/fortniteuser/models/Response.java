package fr.Orantoine.fortniteuser.models;

import org.json.JSONObject;

public class Response {

    private int code;
    private String message;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
