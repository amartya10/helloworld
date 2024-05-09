package com.example.helloworld.ratelimiter;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter {


  ScheduledThreadPoolExecutor threadPool
    = new ScheduledThreadPoolExecutor(1);


  public RateLimiter(){
  }
  public boolean start(){
    threadPool.schedule(new CleanUp(), perSec, TimeUnit.SECONDS);
    return true;
  }

  public boolean stop(){
    threadPool.shutdown();
    return true;

  }
  public AtomicInteger currentCounter = new AtomicInteger(0);
  public  static  int request = 5;
  public  static  int perSec = 60;


  public boolean acquiredToken() {
    boolean flg = false;
    if (currentCounter.get() < request) {
       currentCounter.incrementAndGet();
       flg = true;
    }
    System.out.println(" token accquired: " + flg);

    return  flg;
  }

  public int getCurrentCounter() {
    return currentCounter.get();
  }

  class CleanUp implements Callable {

    public Object _clean() {
      System.out.println("clean up token " + currentCounter);

      currentCounter.set(0);
        return currentCounter;
    }
    @Override
    public Object call() throws Exception {
      return _clean();
    }
  }


}
