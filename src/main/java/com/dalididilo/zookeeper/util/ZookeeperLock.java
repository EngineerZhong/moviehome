package com.dalididilo.zookeeper.util;

import com.dalididilo.zookeeper.bean.Lock;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;
import java.util.stream.Collectors;

public class ZookeeperLock {



    //连接zookeeper服务器。

    private ZkClient zkClient;

    public ZookeeperLock() {
        zkClient = new ZkClient("192.168.1.185",3000,2000);
        // 会话超时时间3000，在会话关闭后，3s将删除其创建的临时节点。
    }

    // 创建临时序号节点。
    public Lock createLockNode(String lockId){
        // 创建的临时节点。
        String path = zkClient.createEphemeralSequential("/dalididilo/" + lockId, "w");
        Lock lock = new Lock();
        lock.setActive(false);
        lock.setLockId(lockId);
        lock.setPath(path);
        return lock;
    }

    /**
     * 1、获得锁。
     * @param lockId
     * @param timeOut
     * @return
     */
    public Lock lock(String lockId,long timeOut){
        Lock lockNode = createLockNode(lockId);
        tryActiveLock(lockNode);
        if(!lockNode.isActive()){
            try {
                synchronized (lockNode){
                    lockNode.wait(timeOut); // 等待
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return lockNode;
    }


    // 2、激活锁。
    public Lock tryActiveLock(Lock lockNode){
        // 判断是否获得锁。
        List<String> lockNodes =  zkClient.getChildren("/dalididilo")
                .stream()
                .sorted()
                .map(p->"/dalididilo/" + p)
                .collect(Collectors.toList());
        String firstPath = lockNodes.get(0);
        if(firstPath.equals(lockNode.getPath())){
            lockNode.setActive(true); // 获得锁。
        }else{
            // 没有获得的情况下要往上一个节点添加节点变更监听。
            String upNodePath = lockNodes.get(lockNodes.indexOf(lockNode.getPath()) - 1); // 获得上一个节点。
            zkClient.subscribeDataChanges(upNodePath, new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {

                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("节点删除。" + s);// 上一个节点删除后，继续尝试获得锁。
                    // 再次尝试激活锁。
                    Lock lock = tryActiveLock(lockNode);
                    synchronized (lockNode){
                        if(lock.isActive()){
                            lockNode.notify();
                        }
                    }
                    zkClient.unsubscribeDataChanges(upNodePath,this);
                }
            });
        }


        return lockNode;
    }


    // 3、释放锁。
    public void unLock(Lock lock){
        if(lock.isActive()){
            zkClient.delete(lock.getPath());
        }
    }



}
