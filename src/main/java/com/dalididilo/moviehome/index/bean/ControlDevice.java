package com.dalididilo.moviehome.index.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ControlDevice implements Serializable {
    /**
     * glbh
     * 管廊编号
     * sbbh
     * 设备编号
     * sblx
     * 设备类型
     * controlCode
     * 控制代码
     * 0:关闭
     * 1:打开
     * 2:远控模式
     * 3:自控模式 注:远控模式和自控模式 为风机、水泵特有的模式。
     */
    @JsonProperty("access_token")
    private String access_token;
    @JsonProperty("sbbh")
    private String sbbh;
    @JsonProperty("glbh")
    private String glbh;
    @JsonProperty("sblx")
    private String sblx;
    @JsonProperty("controlCode")
    private String controlCode;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getSbbh() {
        return sbbh;
    }

    public void setSbbh(String sbbh) {
        this.sbbh = sbbh;
    }

    public String getGlbh() {
        return glbh;
    }

    public void setGlbh(String glbh) {
        this.glbh = glbh;
    }

    public String getSblx() {
        return sblx;
    }

    public void setSblx(String sblx) {
        this.sblx = sblx;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    @Override
    public String toString() {
        return "ControlDevice{" +
                "access_token='" + access_token + '\'' +
                ", sbbh='" + sbbh + '\'' +
                ", glbh='" + glbh + '\'' +
                ", sblx='" + sblx + '\'' +
                ", controlCode='" + controlCode + '\'' +
                '}';
    }
}
