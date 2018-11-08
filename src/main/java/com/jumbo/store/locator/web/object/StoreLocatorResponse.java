package com.jumbo.store.locator.web.object;

public class StoreLocatorResponse<T> {
    //since it is possible that this api is going to be used by another party it would be better to have a response code
    //in response to find out if the state was successful or not
    public static final int SUCCESSFUL = 0;
    public static final int UNSUCCESSFUL = 1;

    private String message;
    private int responseCode;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
