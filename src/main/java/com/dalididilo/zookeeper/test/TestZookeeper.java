package com.dalididilo.zookeeper.test;

import com.dalididilo.zookeeper.bean.Lock;
import com.dalididilo.zookeeper.util.ZookeeperLock;
import org.junit.Test;

public class TestZookeeper {

    @Test
    public void test(){
        ZookeeperLock zookeeperLock = new ZookeeperLock();
        Lock ylj = zookeeperLock.lock("ylj", 365 * 24 * 3600 * 1000);// 一年的毫秒数
        System.out.println("ylj获得锁。");
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
