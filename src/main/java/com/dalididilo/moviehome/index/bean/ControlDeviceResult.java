package com.dalididilo.moviehome.index.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ControlDeviceResult {
    @JsonProperty("code")
    private String code;
    @JsonProperty("info")
    private String info;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
