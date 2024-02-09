package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.processors.HelloWorldRequestProcessor;
import ru.otus.java.basic.http.server.processors.OperationAddRequestProcessor;
import ru.otus.java.basic.http.server.processors.RequestProcessor;
import ru.otus.java.basic.http.server.processors.UnknownRequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> router;
    private RequestProcessor unknownRequestProcessor;

    private static final Logger logger = LogManager.getLogger(Dispatcher.class.getName());

    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put("/add", new OperationAddRequestProcessor());         // /GET /add => OperationAddRequestProcessor
        this.router.put("/hello_world", new HelloWorldRequestProcessor());   // /GET /hello_world => HelloWorldRequestProcessor
        this.unknownRequestProcessor = new UnknownRequestProcessor();
    }

    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        if (!router.containsKey(httpRequest.getUri())) {
            logger.warn("Получили неизвестный запрос - показываем страницу 404.");
            unknownRequestProcessor.execute(httpRequest, output);
            return;
        }
        router.get(httpRequest.getUri()).execute(httpRequest, output);
    }
}
