package com.water.irrigation.utils;

import java.io.Serializable;

/**
 * @author sukang
 */
public class ResponseBeanBuilder {

    private String errorMsg;
    private boolean hasError;
    private Serializable body;


    public ResponseBeanBuilder errorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public ResponseBeanBuilder hasError(boolean hasError) {
        this.hasError = hasError;
        return this;
    }

    public ResponseBeanBuilder body(Serializable body) {
        this.body = body;
        return this;
    }

    ResponseBean builder() {
        return new ResponseBean(body, hasError, errorMsg);
    }

}