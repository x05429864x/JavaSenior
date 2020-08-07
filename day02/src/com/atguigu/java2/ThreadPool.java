package com.atguigu.java2;

import java.util.concurrent.*;

/**
 * 创建线程的方式四：使用线程池
 *
 * 好处：
 * 1.提高响应速度（减少了创建新线程的时间）
 * 2.降低资源消耗（重复利用线程池中线程，不需要每次都创建）
 * 3.便于线程管理
 *      corePoolSize：核心池的大小
 *      maximumPoolSize：最大线程数
 *      keepAliveTime：线程没有任务时最多保持多长时间后会终止
 *
 *
 * 面试题：创建多线程有几种方式？四种！
 * @author shkstart
 * @create 2019-02-15 下午 6:30
 */

class NumberThread implements Runnable{

    @Override
    public void run() {
        for(int i = 0;i <= 100;i++){
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }
}

class NumberThread1 implements Runnable{

    @Override
    public void run() {
        for(int i = 0;i <= 100;i++){
            if(i % 2 != 0){
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }
}

class NumberThread2 implements Callable {

    private int sum = 0;
    @Override
    public Object call() throws InterruptedException {

        for(int i = 0;i <= 100;i++){
            synchronized (NumberThread2.class){
                if(i % 2 == 0){
                    notifyAll();
                    sum += i;
                    wait();
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                }
            }

        }
        System.out.println(sum);
        return sum;
    }
}

public class ThreadPool {

    public static void ES(){
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for(int j=0;j<10;j++){
                        System.out.println(Thread.currentThread().getName()+"  "+j);
                    }
                }
            });
        }
        threadPool.shutdown();
    }
    public static void main(String[] args) {
//        ES();
        //1. 提供指定线程数量的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor service1 = (ThreadPoolExecutor) service;
        //设置线程池的属性
        System.out.println(service.getClass());
        service1.setCorePoolSize(15);
        //活跃时间
//        service1.setKeepAliveTime();


        //2.执行指定的线程的操作。需要提供实现Runnable接口或Callable接口实现类的对象
//        service.execute(new NumberThread());//适合适用于Runnable
//        service.execute(new NumberThread1());//适合适用于Runnable

        Future<?> future = service.submit(new NumberThread2());//适合使用于Callable
        Future<?> future1 = service.submit(new NumberThread2());//适合使用于Callable
        try {
            Object sum = future.get();
            System.out.println("总和为：" + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//3.关闭连接池
        service.shutdown();
    }

}
