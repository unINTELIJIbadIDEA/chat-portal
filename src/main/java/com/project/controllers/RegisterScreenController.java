package com.project.controllers;
import com.project.HelloApplication;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;


public class RegisterScreenController {
    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        // Dodaj akcje dla przycisków
        registerButton.setOnAction(event -> handleLoginAction());
        backButton.setOnAction(event -> handleBackAction());

        registerButton.setOnMouseEntered(this::onMouseEntered);
        registerButton.setOnMouseExited(this::onMouseExited);
        registerButton.setOnMousePressed(this::onMousePressed);
        registerButton.setOnMouseReleased(this::onMouseReleased);

        backButton.setOnMouseEntered(this::onMouseEntered);
        backButton.setOnMouseExited(this::onMouseExited);
        backButton.setOnMousePressed(this::onMousePressed);
        backButton.setOnMouseReleased(this::onMouseReleased);
    }

    // Akcja dla przycisku "Zarejestruj"
    @FXML
    private void handleLoginAction() {
        System.out.println("Zarejestrowano!");
        // Dodaj logikę logowania (np. sprawdzenie danych logowania)
    }

    // Akcja dla przycisku "Powrót"
    @FXML
    private void handleBackAction() {
        System.out.println("Powrót do poprzedniego ekranu.");
        try {
            URL resource = HelloApplication.class.getResource("startscreen.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            AnchorPane loginScreen = fxmlLoader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(loginScreen));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Zmiana koloru tła po najechaniu
    private void onMouseEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #ffe0b2; -fx-border-color: grey; -fx-border-radius: 10px; -fx-background-radius: 10px;");
    }

    // Przywrócenie oryginalnego koloru tła
    private void onMouseExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #fff8e1; -fx-border-color: grey; -fx-border-radius: 10px; -fx-background-radius: 10px;");
    }

    // Efekt kliknięcia (zmiana skali)
    private void onMousePressed(MouseEvent event) {
        Button button = (Button) event.getSource();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
        scaleTransition.setToX(0.9); // Zmniejsz przycisk
        scaleTransition.setToY(0.9);
        scaleTransition.play();
    }

    // Powrót do normalnej skali po kliknięciu
    private void onMouseReleased(MouseEvent event) {
        Button button = (Button) event.getSource();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
        scaleTransition.setToX(1.0); // Przywróć oryginalny rozmiar
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }
}
