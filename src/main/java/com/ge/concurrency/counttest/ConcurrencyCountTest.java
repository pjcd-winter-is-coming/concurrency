package com.ge.concurrency.counttest;

import com.ge.concurrency.annoations.NotThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@NotThreadSafe
public class ConcurrencyCountTest {

     // 请求总数
    public static int clientTotal = 5000;

    // 并发数量
    public static int threadTotal = 200;

    public static int  count = 0;

    public static void main(String[] args) throws Exception{
        // 线程
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++){
            executorService.execute(() ->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("count{}"+count);
    }


    private static void add(){
        count ++;
    }

}
