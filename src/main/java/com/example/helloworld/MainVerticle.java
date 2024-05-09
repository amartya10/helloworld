package com.example.helloworld;

import com.example.helloworld.ratelimiter.RateLimiter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainVerticle extends AbstractVerticle {


  ExecutorService executor = Executors.newFixedThreadPool(5);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    RateLimiter rateLimiter = new RateLimiter();
    rateLimiter.start();

    vertx.createHttpServer().requestHandler(req -> {
      if (rateLimiter.acquiredToken()) {
        req.response()
          .putHeader("content-type", "text/plain")
          .end("Hello from Vert.x!");
      } else {
        req.response()
          .setStatusCode(429)
          .putHeader("content-type", "text/plain")
          .end("To many requests");
      }

    }).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        rateLimiter.stop();
        startPromise.fail(http.cause());
      }
    });
  }
}
