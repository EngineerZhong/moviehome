package com.dalididilo.zookeeper.bean;

public class ServerSmoothPollingBean {


    private String host;
    private String port;
    private Integer weight;
    private Integer currentWeight = 0;


    @Override
    public String toString() {
        return "ServerSmoothPollingBean{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", weight=" + weight +
                ", currentWeight=" + currentWeight +
                '}';
    }

    public ServerSmoothPollingBean(String host, String port, Integer weight, Integer currentWeight) {
        this.host = host;
        this.port = port;
        this.weight = weight;
        this.currentWeight = currentWeight;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Integer currentWeight) {
        this.currentWeight = currentWeight;
    }
}
