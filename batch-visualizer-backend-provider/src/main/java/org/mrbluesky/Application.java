package org.mrbluesky;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Application {


  public static void main(String[] args) {
    Logger logger = LogManager.getLogger(Application.class);
    logger.info("asdasdasdas");
    SpringApplication.run(Application.class, args);

  }

}
