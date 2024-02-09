package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpServer {
    private int port;
    private Dispatcher dispatcher;

    private static final Logger logger = LogManager.getLogger(MainApplication.class.getName());

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    ExecutorService executorService = Executors.newFixedThreadPool(8);
                    executorService.execute(() -> {
                        try {
                            logger.info("Раскачегарили пул потоков (аж 8 штуков)");
                            doRequest(socket);
                        } catch (IOException e) {
                            logger.error("ОшиПка при обработке запроса в отдельном потоке. Памагити.");
                            e.printStackTrace();
                        }
                    });
                    executorService.shutdown();
                    executorService.awaitTermination(30, TimeUnit.SECONDS);
                } catch (IOException e) {
                    logger.error("Ужасающая ошибка");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            logger.error("Очередная ужасающая ошибка");
            e.printStackTrace();
        }
    }

    private void doRequest(Socket socket) throws IOException { // таки беда с неймингом
        byte[] buffer = new byte[8192];
        int n = socket.getInputStream().read(buffer);
        String rawRequest = new String(buffer, 0, n);
        HttpRequest httpRequest = new HttpRequest(rawRequest);
        dispatcher.execute(httpRequest, socket.getOutputStream());
    }
}
