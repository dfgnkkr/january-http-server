package ru.otus.java.basic.http.server.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class OperationAddRequestProcessor implements RequestProcessor {

    private static final Logger logger = LogManager.getLogger(OperationAddRequestProcessor.class.getName());

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        int a = 0;
        int b = 0;
        String result = "";
        try {
            a = Integer.parseInt(httpRequest.getParameter("a"));
            b = Integer.parseInt(httpRequest.getParameter("b"));
            result = a + " + " + b + " = " + (a + b);
        } catch(NumberFormatException e){
            logger.error("В запросе отсутсвуют или невалидные параметры. Покажем тогда пользователю альтернативно одарённый текст на странице.");
            result = "Математика сломалась ;( Пришлите красивые две цифры, пжлста!";
            e.printStackTrace();
        }
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=utf-8\r\n\r\n<html><body><h1>" + result + "</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
