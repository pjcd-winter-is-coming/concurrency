package com.ge.concurrency.example.atomic;

import com.ge.concurrency.annoations.ThreadSafe;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class AtomicConcurrencyCountTest {

    public static int clientTotal = 5000;

    public static int threadTotal = 200;

    // 线程安全的integer
    public static AtomicInteger atomicInteger = new AtomicInteger(0);

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
        System.out.println("count{}"+atomicInteger.get());
    }

   private static  void add(){
       atomicInteger.incrementAndGet();
   }

}
