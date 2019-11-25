package com.dalididilo.zookeeper.util;


import com.dalididilo.zookeeper.bean.ServerData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/***
 * 平滑轮询算法。
 */
public class ZkPollingAlgorithm {

    private Integer currentIndex = 1;
    private Integer balanceSum = 0;
    private List<ServerData> servers = new ArrayList<>();
    private List<ServerData> current = new ArrayList<>();
    Integer[] count = new Integer[]{0,0,0,0};
    @Test
    public void test() {
        servers.add(new ServerData("192.168.0.100", "3333", 5));
        servers.add(new ServerData("192.168.0.101", "3334", 3));
        servers.add(new ServerData("192.168.0.102", "3335", 1));
        servers.add(new ServerData("192.168.0.103", "3336", 9));
        while(currentIndex <= 36){
            algorithmPolling();
            currentIndex ++;
        }

        System.out.println("100:101:102:103 = ");
        for(Integer num: count){
            System.out.print(num + ":");
        }

    }


    public void algorithmPolling() {
        if (currentIndex == 1) {
            for (int i = 0; i < servers.size(); i++) {
               balanceSum += servers.get(i).getBalance();
               current.add(new ServerData("","",0));
            }
        }
        Integer max = 0;
        Integer maxIndex = 0;
        for (int i = 0; i < servers.size(); i++) {
            Integer temp = servers.get(i).getBalance() + current.get(i).getBalance();
            current.set(i, current.get(i).setBalanceTemp(temp));
            if (temp > max) {
                max = temp;
                maxIndex = i;
            }

        }
        current.set(maxIndex, current.get(maxIndex).setBalanceTemp(max - balanceSum));
        System.out.println("命中服务器:" + this.servers.get(maxIndex).toString());
        count[maxIndex] += 1;

    }


}
