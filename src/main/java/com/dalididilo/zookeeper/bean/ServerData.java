package com.dalididilo.zookeeper.bean;

public class ServerData {

    private String host;
    private String port;
    private Integer balance;



    public ServerData(String host, String port, Integer balance) {
        this.host = host;
        this.port = port;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ServerData{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", balance=" + balance +
                '}';
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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public ServerData setBalanceTemp(Integer balance){
        this.balance = balance;
        return this;
    }
}
