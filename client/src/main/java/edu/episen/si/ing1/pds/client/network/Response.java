package edu.episen.si.ing1.pds.client.network;

import com.fasterxml.jackson.databind.JsonNode;


public class Response {
    private boolean success;
    private Object message;

    public boolean isSuccess() {
        return success;
    }

    public Object getMessage() {
        return message;
    }
}
