# Hello World Microservice with RateLimiter


## Ratelimiter config is hard code for now

   rate limit =  5 request/minute

## How to run

  ```shell
   cd 'helloworld'
  ```

  ```shell
    docker build -t com.example/helloworld .
   ```
  ```shell
    docker run --name helloworld -p 127.0.0.1:8888:8888 com.example/helloworld
  ```

   ```shell
    curl -v http://localhost:8888
  ```

