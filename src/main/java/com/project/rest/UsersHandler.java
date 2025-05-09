package com.project.rest;

//import com.project.dao.DatabaseConnection;
import com.project.utils.Config;
import com.project.dao.UsersDAO;
import com.project.models.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.project.server.ApiServer.*;

public class UsersHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        try {
            if (method.equalsIgnoreCase("POST")) {
                handlePostRequest(exchange);
            } else if (method.equalsIgnoreCase("GET")) {
                handleGetRequest(exchange);
            }
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleGetRequest(HttpExchange exchange) throws SQLException, IOException {
        Map<String, String> queryParams = getQueryParams(exchange.getRequestURI().getQuery());
        String userId = queryParams.get("userId");
        if (userId != null) {
            UsersDAO dao = new UsersDAO(Config.getDbUrl(), Config.getDbUsername(), Config.getDbPassword());
            dao.connect();
            String user = dao.getUserWithId(Integer.parseInt(userId));
            if (user.isEmpty()) {
                sendResponse(exchange, 500, "Failed to find user with specific id");
            } else {
                sendResponse(exchange, 200, user);
            }

            dao.close();
        }

    }

    public Map<String, String> getQueryParams(String requestParams) {
        if (requestParams == null) {
            return Map.of();
        }

        return Stream.of(requestParams.split("&"))
                .map(p -> p.split("="))
                .collect(Collectors.toMap(pp -> pp[0], pp -> pp[1]));
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException, SQLException {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String requestBody = bufferedReader.lines().collect(Collectors.joining("\n"));
        User newUser = gson.fromJson(requestBody, User.class);


        UsersDAO dao = new UsersDAO(Config.getDbUrl(), Config.getDbUsername(), Config.getDbPassword());
        dao.connect();
        boolean isAdded = dao.addUser(newUser);

        if (isAdded) {
            sendResponse(exchange, 201, "User added successfully");
        } else {
            sendResponse(exchange, 500, "Failed to add user");
        }

        dao.close();
        reader.close();
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String responseContent) throws IOException {
        exchange.sendResponseHeaders(statusCode, responseContent.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseContent.getBytes());
        os.close();
    }
}

