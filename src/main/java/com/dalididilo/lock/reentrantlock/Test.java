package com.dalididilo.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class Test {



    @org.junit.Test
    public void test(){
        ReentrantLock lock = new ReentrantLock();

        for (int i = 1; i <= 3; i++) {
            lock.lock();
        }

        for(int i=1;i<=3;i++){
            try {

            } finally {
                lock.unlock();
            }
        }
    }
}
