package com.lgh.util;

import com.lgh.util.logging.LogUtil;

public class NotifyTest {  
    private String flag[] = { "true" };  
  
    class NotifyThread extends Thread {  
        public NotifyThread(String name) {  
            super(name);  
        }  
  
        @Override
		public void run() {  
            try {  
                sleep(3000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            synchronized (flag) {  
                flag[0] = "false";  
                flag.notifyAll();  
            }  
           
        }  
    };  
  
    class WaitThread extends Thread {  
        public WaitThread(String name) {  
            super(name);  
        }  
  
        @Override
		public void run() {  
            synchronized (flag) {  
                while (flag[0] != "false") {  
                    LogUtil.log(getName() + " begin waiting!");  
                    long waitTime = System.currentTimeMillis();  
                    try {  
                        flag.wait();  
  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                    waitTime = System.currentTimeMillis() - waitTime;  
                    LogUtil.log("wait time :" + waitTime);  
                }  
                LogUtil.log(getName() + " end waiting!");  
            }  
        }  
    }  
  
    public static void main(String[] args) throws InterruptedException {  
        LogUtil.log("Main Thread Run!");  
        NotifyTest test = new NotifyTest();  
        NotifyThread notifyThread = test.new NotifyThread("notify01");  
        WaitThread waitThread01 = test.new WaitThread("waiter01");  
        WaitThread waitThread02 = test.new WaitThread("waiter02");  
        WaitThread waitThread03 = test.new WaitThread("waiter03");  
        notifyThread.start();  
        waitThread01.start();  
        waitThread02.start();  
        waitThread03.start();  
    }  
  
}  
