package com.ontotext.s4.service;


public interface S4AbstractClient {

    /**
     *
     * @param requestCompression Whether to receive compressed result or regular
     */
    void setRequestCompression(boolean requestCompression);
}
