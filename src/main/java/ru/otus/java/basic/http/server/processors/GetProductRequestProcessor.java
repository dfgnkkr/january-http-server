package ru.otus.java.basic.http.server.processors;

import com.google.gson.Gson;
import ru.otus.java.basic.http.server.HttpRequest;
import ru.otus.java.basic.http.server.entities.Product;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetProductRequestProcessor implements RequestProcessor {
    private Gson gson;

    public GetProductRequestProcessor() {
        gson = new Gson();
    }

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        Product product = gson.fromJson("{\"id\"=4, \"title\"=\"test\", \"category\":{\"id\"=44, \"title\"=\"testCategory\"}}", Product.class);
        System.out.println(product);

        String response = "HTTP/1.1 200 OK\r\nContent-Type:json/html\r\n\r\n" + product.toString() + "";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
