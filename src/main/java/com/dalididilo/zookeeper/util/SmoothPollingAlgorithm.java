package com.dalididilo.zookeeper.util;

import com.dalididilo.zookeeper.bean.ServerSmoothPollingBean;

import java.util.ArrayList;
import java.util.List;

public class SmoothPollingAlgorithm {



    private List<ServerSmoothPollingBean> servers = new ArrayList<>();

    private Integer weightSum = 0;


    public void initServers(List<ServerSmoothPollingBean> servers){
        this.servers = servers;
        this.weightSum = this.servers.stream().map(server -> server.getWeight()).reduce(0, (i, j) -> i + j);
    }


    public ServerSmoothPollingBean selectServer(){
        ServerSmoothPollingBean serverTemp = null;

        for(ServerSmoothPollingBean temp : servers){
            temp.setCurrentWeight(temp.getWeight()+temp.getCurrentWeight());
            if(serverTemp == null || serverTemp.getCurrentWeight() < temp.getCurrentWeight()){
                serverTemp = temp;
            }
        }

        serverTemp.setCurrentWeight(serverTemp.getCurrentWeight() - weightSum);
        System.out.println(serverTemp.toString());
        return serverTemp;
    }
}
