package com.dalididilo.zookeeper.test;

import com.dalididilo.zookeeper.bean.Lock;
import com.dalididilo.zookeeper.util.ZookeeperLock;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;

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



    @Test
    public void testWatch() throws Exception {
        // zk客户端
        Stat st = new Stat();
        ZooKeeper zk = new ZooKeeper("192.168.1.186:2181,192.168.1.186:2182", 3000, new Watcher() {
            // 事件通知回调方法
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getState());
                System.out.println(watchedEvent.getType());
                System.out.println(watchedEvent.getPath());
            }
        });
        // 在根目录下创建一个/dalididilo ，数据为大离弟弟咯的持久序列号节点。
//        zk.create("/dalididilo","大离弟弟咯。".getBytes("utf-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println(new String(zk.getData("/dalididilo0000000005",true,st)));
        System.out.println(st.getVersion());
//        -1表示不指定版本。
        zk.setData("/dalididilo0000000005","大离妹妹咯".getBytes(),-1);
        zk.close();
    }
}
