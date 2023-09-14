package com.maxim.hibernate.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {
    
    private static Logger log = LogManager.getLogger(LogTest.class);

    public static void main(String[] args) {
       System.out.println("This is popop");
       doWork();
       log.info("info");
       log.trace("trace");
       log.warn("warn");
       log.debug("debug");
       log.error("Error");
       log.fatal("fatal");
       log.info("info");
       log.info("info");
       log.trace("trace");
       log.warn("warn");
       log.debug("debug");
       log.error("Error");
       log.fatal("fatal");
       log.trace("trace");
       log.warn("warn");
       log.debug("debug");
       log.error("Error");
       log.fatal("fatal");


    }

    public static void doWork() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
               log.info("Hello {}", i);
            }
        });

        t1.start();
    }
}
