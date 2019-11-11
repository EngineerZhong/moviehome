package com.dalididilo.moviehome.utils;


import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperProSync implements Watcher,Runnable {

//    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
//                connectedSemaphore.countDown();
            } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":::::::配置已修改，新值为：" + new String(zk.getData(watchedEvent.getPath(), true, stat)));
                } catch (Exception e) {


                }
            }
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        String path = "/username";
        try {
            zk = new ZooKeeper("192.168.1.150", 5000, new ZookeeperProSync());
//            connectedSemaphore.await();
            System.out.println(new String(zk.getData(path, true, stat)));
            while (true){
                System.out.println(Thread.currentThread().getName() + ":::: Running");
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
