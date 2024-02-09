package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainApplication {

    private static final Logger logger = LogManager.getLogger(MainApplication.class.getName());

    // Домашнее задание:
    // - Добавить логирование
    // - Добавить обработку запросов в параллельных потоках

    public static void main(String[] args) {
        logger.info("Hello world, test log-message");
        HttpServer server = new HttpServer(Integer.parseInt((String)System.getProperties().getOrDefault("port", "8189")));
        server.start();
    }
}

/**
 * HTTP/1.1 200 OK
 * Content-Type: text/html
 *
 * <html>
 *     <body>
 *         <h1>Hello World!</h1>
 *     </body>
 * </html>
 */