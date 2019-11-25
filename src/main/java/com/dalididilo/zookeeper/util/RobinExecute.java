package com.dalididilo.zookeeper.util;

import com.dalididilo.zookeeper.bean.ServerSmoothPollingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class RobinExecute {

    /** 线程使用完不会清除该变量,会一直保留着，由于线程 池化所以不用担心内存泄漏 **/
    private ThreadLocal<SmoothPollingAlgorithm> weightRoundRobinTl = new ThreadLocal<SmoothPollingAlgorithm>();

    private ReentrantLock lock = new ReentrantLock();

    /** 为什么添加volatile，是因为 ReentrantLock 并不保证内存可见性 **/
    private volatile SmoothPollingAlgorithm smoothWeightRoundRobin;

    public static void main(String[] args) {

        RobinExecute robinExecute = new RobinExecute();

        /** ==================    TheadLocal   ========================**/
        robinExecute.acquireWeightRoundRobinOfTheadLocal().selectServer();

        /** ==================    ReentrantLock 可重入锁   ========================**/
        robinExecute.getLock().lock();  //notice: 注意此锁会无休止的等待资源，如果使用此锁，无比保证资源能够被拿到
        try {
            robinExecute.acquireWeightRoundRobinOfLock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally { //确保一定要释放锁
            robinExecute.getLock().unlock();
        }

    }

    /**
     * 在分布式情况，第二种使用方式  使用cas ReentrantLock 可重入锁
     * notice:
     * @return
     */
    public ServerSmoothPollingBean acquireWeightRoundRobinOfLock() {
        if (smoothWeightRoundRobin == null) {
            SmoothPollingAlgorithm weightRoundRobin = new SmoothPollingAlgorithm();
            List<ServerSmoothPollingBean> servers = new ArrayList<>();
            servers.add(new ServerSmoothPollingBean("191", "997",1, 0));
            servers.add(new ServerSmoothPollingBean("192", "998",2, 0));
            servers.add(new ServerSmoothPollingBean("194", "999",4, 0));
            weightRoundRobin.initServers(servers);
            smoothWeightRoundRobin = weightRoundRobin;
        }
        return smoothWeightRoundRobin.selectServer();
    }

    /**
     * 在分布式情况，第一种使用方式  ThreadLock
     * notice: 只有在使用池化技术的情况才建议使用此方式，否则达不到效果，还会造成内存泄漏
     * @return
     */
    public SmoothPollingAlgorithm acquireWeightRoundRobinOfTheadLocal() {
        return Optional.ofNullable(weightRoundRobinTl.get())
                .orElseGet(() -> {
                    SmoothPollingAlgorithm weightRoundRobin = new SmoothPollingAlgorithm();
                    List<ServerSmoothPollingBean> servers = new ArrayList<>();
                    servers.add(new ServerSmoothPollingBean("191", "997",1, 0));
                    servers.add(new ServerSmoothPollingBean("192", "998",2, 0));
                    servers.add(new ServerSmoothPollingBean("194", "999",4, 0));
                    weightRoundRobin.initServers(servers);
                    weightRoundRobinTl.set(weightRoundRobin);
                    return weightRoundRobin;
                });
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public ThreadLocal<SmoothPollingAlgorithm> getWeightRoundRobinTl() {
        return weightRoundRobinTl;
    }
}
