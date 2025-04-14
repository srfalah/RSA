package com.example.rsa;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private VBox EncryptionPane;

    @FXML
    private Button decryptButton;

    @FXML
    private TextField decryptedMessageField;

    @FXML
    private Button encryptButton;

    @FXML
    private TextField encryptedMessageField;

    @FXML
    private Button generateEncKeysButton;

    @FXML
    private TextField messageField;

    @FXML
    private TextField privateKeyField;

    @FXML
    private TextField publicKeyField;

    @FXML
    private TextField value_PHIn_Field;

    @FXML
    private TextField value_d_Field;

    @FXML
    private TextField value_e_Field;

    @FXML
    private TextField value_n_Field;

    @FXML
    private TextField value_p_Field;

    @FXML
    private TextField value_q_Field;

    private BigInteger p, q, n, phi, e, d;
    private PauseTransition primeValidationDelay = new PauseTransition(Duration.millis(200));

    private void setupListeners() {
        value_p_Field.textProperty().addListener((obs, oldVal, newVal) -> {
            primeValidationDelay.setOnFinished(event -> validatePrimeInputs());
            primeValidationDelay.playFromStart();
        });
        value_q_Field.textProperty().addListener((obs, oldVal, newVal) -> {
            primeValidationDelay.setOnFinished(event -> validatePrimeInputs());
            primeValidationDelay.playFromStart();
        });
        value_e_Field.textProperty().addListener((obs, oldVal, newVal) -> validateE());
        messageField.textProperty().addListener((obs, oldVal, newVal) -> encryptButton.setDisable(newVal.trim().isEmpty()));
        encryptedMessageField.textProperty().addListener((obs, oldVal, newVal) -> decryptButton.setDisable(newVal.trim().isEmpty()));
    }

    private void validatePrimeInputs() {
        try {
            if (!value_p_Field.getText().isEmpty() && !value_q_Field.getText().isEmpty()) {
                p = new BigInteger(value_p_Field.getText());
                q = new BigInteger(value_q_Field.getText());
                if (!p.isProbablePrime(10) || !q.isProbablePrime(10)) {
                    showNotification("Both numbers must be prime.");
                    return;
                }
                calculateNAndPhi();
                checkIfReadyForE();
            }
        } catch (NumberFormatException ex) {
            showNotification("Please enter valid numbers.");
        }
    }

    private void calculateNAndPhi() {
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        value_n_Field.setText(n.toString());
        value_PHIn_Field.setText(phi.toString());
    }

    private void checkIfReadyForE() {
        if (value_e_Field.getText() != null && !value_e_Field.getText().isEmpty()) {
            validateE();
        }
    }

    private void validateE() {
        try {
            if (phi != null && !value_e_Field.getText().isEmpty()) {
                e = new BigInteger(value_e_Field.getText());
                if (!e.gcd(phi).equals(BigInteger.ONE)) {
                    showNotification("e must be coprime with φ(n)");
                    return;
                }
                d = e.modInverse(phi);
                value_d_Field.setText(d.toString());
                if (e != null && d != null && n != null) {
                    generateEncKeysButton.setDisable(false);
                }
            }
        } catch (Exception ex) {
            showNotification("Invalid value for e.");
        }
    }

    @FXML
    private void onGeneratePrimes() {
        SecureRandom random = new SecureRandom();
        p = getRandomPrimeUnder10000(random);
        do {
            q = getRandomPrimeUnder10000(random);
        } while (p.equals(q));

        value_p_Field.setText(p.toString());
        value_q_Field.setText(q.toString());
        calculateNAndPhi();
        checkIfReadyForE();
    }

    private BigInteger getRandomPrimeUnder10000(SecureRandom random) {
        BigInteger candidate;
        do {
            candidate = BigInteger.valueOf(random.nextInt(10000) + 1);
        } while (!candidate.isProbablePrime(10));
        return candidate;
    }

    @FXML
    private void onGenerateKeys() {
        publicKeyField.setText("{" + e + ", " + n + "}");
        privateKeyField.setText("{" + d + ", " + n + "}");
        EncryptionPane.setDisable(false);
        if (messageField.getText() != null && !messageField.getText().isEmpty()) {
            encryptButton.setDisable(false);
        }
    }

    @FXML
    private void onEncrypt() {
        try {
            BigInteger message = new BigInteger(messageField.getText());
            if (message.compareTo(n) >= 0) {
                showNotification("Message must be less than n.");
                return;
            }
            BigInteger encrypted = message.modPow(e, n);
            encryptedMessageField.setText(encrypted.toString());
            decryptButton.setDisable(false);
        } catch (Exception ex) {
            showNotification("Message must be a valid number.");
        }
    }

    @FXML
    private void onDecrypt() {
        try {
            BigInteger encrypted = new BigInteger(encryptedMessageField.getText());
            BigInteger decrypted = encrypted.modPow(d, n);
            decryptedMessageField.setText(decrypted.toString());
        } catch (Exception ex) {
            showNotification("Encrypted message must be a valid number.");
        }
    }

    @FXML
    private void clearEverything() {
        value_p_Field.clear();
        value_q_Field.clear();
        value_n_Field.clear();
        value_PHIn_Field.clear();
        value_e_Field.clear();
        value_d_Field.clear();
        publicKeyField.clear();
        privateKeyField.clear();
        messageField.clear();
        encryptedMessageField.clear();
        decryptedMessageField.clear();
        EncryptionPane.setDisable(true);
        generateEncKeysButton.setDisable(true);
        encryptButton.setDisable(true);
        decryptButton.setDisable(true);
    }

    @FXML
    private void closeTheProgram() {
        Platform.exit();
    }

    private void showNotification(String message) {
        Platform.runLater(() -> {
            Notifications.create()
                    .title("Error!!!")
                    .text(message)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_CENTER)
                    .showError();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupListeners();
        EncryptionPane.setDisable(true);
        generateEncKeysButton.setDisable(true);
        encryptButton.setDisable(true);
        decryptButton.setDisable(true);
    }
}
