package com.kopasolar.controllers.wrappers;

/**
 * Class name: ResponseWrapper
 * Creater: wgicheru
 * Date:1/27/2020
 */
public class ResponseWrapper {
    String status;
    Object data;

    public ResponseWrapper() {
    }

    public ResponseWrapper(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
