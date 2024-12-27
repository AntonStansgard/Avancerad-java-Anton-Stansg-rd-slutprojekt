package com.javafxtodoapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class ToDoController  {

    //Dummy fält för att kunna editera/ta bort aktivitet
    @FXML
    private TextField idField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Label statusLabel; // För feedback till användaren

    @FXML
    private TableView<Todo> todoTable;

    @FXML
    private TableColumn<Todo, Integer> idColumn;

    @FXML
    private TableColumn<Todo, String> titleColumn;

    @FXML
    private TableColumn<Todo, String> descriptionColumn;

    private ObservableList<Todo> todoList = FXCollections.observableArrayList();
    private final String API_URL = "http://localhost:8081/todos"; //URL för webserver (backend)

    // Initiera fält och ladda todo-data vid uppstart
    @FXML
    public void initialize() {
        // Koppla kolumnerna i TableView till klassen Todo:s fält
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Sätt TableView's items till ObservableList
        todoTable.setItems(todoList);
        //Hämta alla todos med GET-request till servern (backend)
        loadData();
    }

    // Hämta alla todo-poster med GET-request
    @FXML
    void loadData() {
        try {
            // Skapa HTTP-förfrågan
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Läs in svaret som en JSON-sträng
            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            // Deserialisera JSON till en lista av Todo-objekt
            ObjectMapper mapper = new ObjectMapper();
            List<Todo> activities = mapper.readValue(response, new TypeReference<List<Todo>>() {});

            // Uppdatera ObservableList och TableView
            todoList.setAll(activities);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lägg till en ny todo med POST-request
    @FXML
    void addNewToDo () {
        // Validera input, hämta data från redigerbara fält
        String title = titleField.getText();
        String description = descriptionField.getText();

        if (title.isEmpty() || description.isEmpty()) {
            this.statusLabel.setText("Title and description cannot be empty.");
            return;
        }
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ObjectMapper objectMapper = new ObjectMapper();

            // Skapa ett JSON-objekt
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("title", title);
            jsonNode.put("description", description);

            // Konvertera till JSON-sträng
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonString.getBytes(StandardCharsets.UTF_8));
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                loadData(); // Uppdatera tabellen
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Ta bort en befintlig todo-post med DELETE-request
    @FXML
    void deleteTodo() {
        Todo selectedTodo = todoTable.getSelectionModel().getSelectedItem();
        if (selectedTodo != null) {
           idField.setText(Integer.toString(selectedTodo.getId()));
           titleField.setText(selectedTodo.getTitle());
           descriptionField.setText(selectedTodo.getDescription());
        } else {
            System.out.println("No row is selected.");
            return;
        }
        try {
            URL url = new URL(API_URL + "/" + selectedTodo.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.getResponseCode(); // Triggar anropet

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
              loadData(); // Uppdatera tabellen
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Uppdatera en befintlig todo med PUT-request
    @FXML
    void updateTodo() {
        String myId = idField.getText();
        String title = titleField.getText();
        String description = descriptionField.getText();

        if (title.isEmpty() || myId.isEmpty()) {
            this.statusLabel.setText("No row is selected.");
            return;
        }
        try {
            URL url = new URL(API_URL + "/" + Integer.parseInt(myId));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ObjectMapper objectMapper = new ObjectMapper();

            // Skapa ett JSON-objekt
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("title", title);
            jsonNode.put("description", description);

            // Konvertera till JSON-sträng
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonString.getBytes(StandardCharsets.UTF_8));
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                loadData(); // Uppdatera tabellen
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Flytta data från den markerade raden till fälten som är öppna för redigering
    @FXML
    void handleRowSelection() {
        Todo selectedTodo = todoTable.getSelectionModel().getSelectedItem();
        if (selectedTodo != null) {
            idField.setText(Integer.toString(selectedTodo.getId()));
            titleField.setText(selectedTodo.getTitle());
            descriptionField.setText(selectedTodo.getDescription());
        } else {
            System.out.println("No row is selected.");
        }
    }
}
