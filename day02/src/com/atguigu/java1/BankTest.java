package com.atguigu.java1;

/**
 * 使用同步机制将单例模式中的懒汉式改写为线程安全的
 *
 * @author shkstart
 * @create 2019-02-15 下午 2:50
 */
public class BankTest {
    //线程不安全

    /**
     * pool-1-thread-3:com.atguigu.java1.Bank@1d1ae28f
     * pool-1-thread-5:com.atguigu.java1.Bank@1d1ae28f
     * pool-1-thread-2:com.atguigu.java1.Bank@1d1ae28f
     *
     * pool-1-thread-1:com.atguigu.java1.Bank@10ddb982
     *
     * pool-1-thread-7:com.atguigu.java1.Bank@1d1ae28f
     * pool-1-thread-9:com.atguigu.java1.Bank@1d1ae28f
     * @param args
     */
    public static void main(String[] args) {
        TTT t = new TTT();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
//        Thread t3 = new Thread(t);
        t1.start();
        t2.start();
//        t3.start();
//        ExecutorService threadPool = Executors.newFixedThreadPool(20);
//        for (int i = 0; i < 20; i++) {
//            threadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(Thread.currentThread().getName() + ":" + Bank.getInstance());
//                }
//            });
//        }
//        threadPool.shutdown();
    }
}

class TTT implements Runnable{

    private static int number = 10;
    @Override
    public void run() {
        while (true){
            synchronized (this){
                notify();
                if (number>0){
                    try {
                        Thread.sleep(100);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":" + number);
                    number--;
                    try {
                        wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    break;
                }
            }
        }
    }
}

class Bank{

    private Bank(){}

    private static Bank instance = null;

    public static Bank getInstance(){
        //方式一：效率稍差
//        synchronized (Bank.class) {
//            if(instance == null){
//
//                instance = new Bank();
//            }
//            return instance;
//        }
        //方式二：效率更高
//        if(instance == null){

//            synchronized (Bank.class) {
                if(instance == null){

                    instance = new Bank();
                }

//            }
//        }
        return instance;
    }

}
