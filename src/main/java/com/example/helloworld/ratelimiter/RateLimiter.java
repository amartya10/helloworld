package com.example.helloworld.ratelimiter;

import io.netty.handler.logging.LogLevel;
//import io.vertx.core.impl.logging.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;
public class RateLimiter {
  private final static Logger LOGGER =
    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


  public RateLimiter(){
  }
  public boolean start(){

    scheduler.scheduleAtFixedRate(new CleanUp(), 0, perSec, TimeUnit.SECONDS);
    return true;
  }

  public boolean stop(){
    scheduler.shutdown();
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
    LOGGER.log(Level.INFO, "token acquired  " + flg);
    return  flg;
  }

  public int getCurrentCounter() {
    return currentCounter.get();
  }

  class CleanUp implements Runnable {

    public Object _clean() {

      LOGGER.log(Level.INFO, "clean up token  " + currentCounter);

      currentCounter.set(0);
      return currentCounter;
    }
    @Override
    public void run() {
      _clean();
    }
  }


}
