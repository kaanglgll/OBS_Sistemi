package org.example.demo1;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.List;
//arayüz sayfası
//StudentApp ana uygulama sınıfı
public class StudentApp extends Application {
    private final StudentManager studentManager = new StudentManager();
    //dosya sınıfı oluşturduk
    private final FileManager fileManager = new FileManager("students.ost");
    //öğrenci verilerini görsel olarak saklamamızı sağlayacak tablo
    private TableView<Cars> tableView = new TableView<>();

    //uygulamanın başlangıcı
    @Override
    public void start(Stage primaryStage) {
        // Load data from file
        studentManager.setStudents(fileManager.loadStudentsFromFile());
        tableView.getItems().addAll(studentManager.getStudents());

        // Add columns to the table
        TableColumn<Cars, String> nameColumn = new TableColumn<>("Ad");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        nameColumn.setPrefWidth(250);
        nameColumn.setCellFactory(column -> {
            return new TableCell<Cars, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item);
                        setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
                    } else {
                        setText(null);
                        setStyle(""); // Varsayılan stil
                    }
                }
            };
        });

        TableColumn<Cars, String> surnameColumn = new TableColumn<>("Soyad");
        surnameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBaseCode()));
        surnameColumn.setPrefWidth(250);
        surnameColumn.setCellFactory(column -> {
            return new TableCell<Cars, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item);
                        setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
                    } else {
                        setText(null);
                        setStyle(""); // Varsayılan stil
                    }
                }
            };
        });

        TableColumn<Cars, String> departmentColumn = new TableColumn<>("Bölüm");
        departmentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getImage()));
        departmentColumn.setPrefWidth(250);
        departmentColumn.setCellFactory(column -> {
            return new TableCell<Cars, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item);
                        setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
                    } else {
                        setText(null);
                        setStyle(""); // Varsayılan stil
                    }
                }
            };
        });

        TableColumn<Cars, Integer> numberColumn = new TableColumn<>("Numara");
        numberColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getDate()).asObject());
        numberColumn.setPrefWidth(250);
        numberColumn.setCellFactory(column -> {
            return new TableCell<Cars, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item.toString());  // `item` Integer olduğu için `toString()` kullanılır
                        setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
                    } else {
                        setText(null);
                        setStyle(""); // Varsayılan stil
                    }
                }
            };
        });
        tableView.setRowFactory(tv -> {
            TableRow<Cars> row = new TableRow<>() {
                @Override
                protected void updateItem(Cars item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        // Eğer satır boşsa (item == null), farklı renk uygula
                        setStyle("-fx-background-color: lightgray;");
                    } else {
                        // Eğer satır doluysa, varsayılan rengini uygula
                        setStyle(""); // Varsayılan stil
                    }
                }
            };
            return row;
        });

        tableView.getColumns().addAll(nameColumn, surnameColumn, departmentColumn, numberColumn);

        // Input fields
        TextField nameField = new TextField();
        nameField.setPromptText("Ad");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Soyad");

        TextField departmentField = new TextField();
        departmentField.setPromptText("Bölüm");

        TextField numberField = new TextField();
        numberField.setPromptText("Numara");

        TextField searchField = new TextField();
        searchField.setPromptText("Numaraya Göre Ara");

        // Buttons
        Button addButton = new Button("Ekle");
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String department = departmentField.getText();
                String numberText = numberField.getText();
                if (name.isEmpty() || surname.isEmpty() || department.isEmpty() || numberText.isEmpty()) {
                    // Eğer herhangi bir alan boşsa, uyarı veriyoruz ve işlemi sonlandırıyoruz
                    System.out.println("Lütfen tüm alanları doldurun!");
                    
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Eksik Bilgi");
                    alert.setHeaderText(null);
                    alert.setContentText("Lütfen tüm alanları doldurun!");
                    alert.showAndWait();
                    
                    return; // Kodun devamını engeller
                }
                int number = Integer.parseInt(numberField.getText());
                Cars student = new Cars(name, surname, department, number);
                
                if(studentManager.addStudent(student)==-1)
                {                
                	System.out.println("Ogrenci numarası mevcut!");
                
                	Alert alert = new Alert(Alert.AlertType.WARNING);
                	alert.setTitle("Numara Mevcut");
                	alert.setHeaderText(null);
                	alert.setContentText("Ogrenci numarası mevcut!");
                	alert.showAndWait();}
                else {
                	studentManager.addStudent(student);
                	tableView.getItems().add(student);
                	fileManager.saveStudentsToFile(studentManager.getStudents());
                	nameField.clear();
                	surnameField.clear();
                	departmentField.clear();
                	numberField.clear();
            	}	
            } 	
            catch (NumberFormatException ex) {
                System.out.println("Lütfen sayı giriniz!");
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Yanlış Tip");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen sayı giriniz!");
                alert.showAndWait();
            }
        });

        Button deleteButton = new Button("Sil");
        deleteButton.setOnAction(e -> {
            Cars selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                studentManager.deleteStudent(selected.getDate());
                tableView.getItems().remove(selected);
                fileManager.saveStudentsToFile(studentManager.getStudents());
            }
        });

        Button updateButton = new Button("Güncelle");
        updateButton.setOnAction(e -> {
            try {
                Cars selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    String name = nameField.getText().isEmpty() ? selected.getName() : nameField.getText();
                    String surname = surnameField.getText().isEmpty() ? selected.getBaseCode() : surnameField.getText();
                    String department = departmentField.getText().isEmpty() ? selected.getImage() : departmentField.getText();
                    int number = numberField.getText().isEmpty() ? selected.getDate() : Integer.parseInt(numberField.getText());

                    studentManager.updateStudent(selected.getDate(), name, surname, department);
                    tableView.getItems().clear();
                    tableView.getItems().addAll(studentManager.getStudents());
                    fileManager.saveStudentsToFile(studentManager.getStudents());

                    nameField.clear();
                    surnameField.clear();
                    departmentField.clear();
                    numberField.clear();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Lütfen sayı giriniz!");
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Yanlış Tip");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen sayı giriniz!");
                alert.showAndWait();
            }
        });

        Button sortButton = new Button("Sırala");
        sortButton.setOnAction(e -> {
            studentManager.sortingStudent();
            tableView.getItems().clear();
            tableView.getItems().addAll(studentManager.getStudents());
        });

        Button searchButton = new Button("Ara");
        searchButton.setOnAction(e -> {
            try {
                int searchNumber = Integer.parseInt(searchField.getText());
                List<Cars> students = studentManager.getStudents();
                tableView.getItems().clear();
                for (Cars student : students) {
                    if (student.getDate() == searchNumber) {
                        tableView.getItems().add(student);
                        break;
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("Lütfen sayı giriniz!");
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Yanlış Tip");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen sayı giriniz!");
                alert.showAndWait();
            }
        });
        addButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black; -fx-border-color: darkblue; -fx-border-width: 2px;");
        deleteButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black; -fx-border-color: darkblue; -fx-border-width: 2px;");
        updateButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black; -fx-border-color: darkblue; -fx-border-width: 2px;");
        sortButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black; -fx-border-color: darkblue; -fx-border-width: 2px;");
        searchButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black; -fx-border-color: darkblue; -fx-border-width: 2px;");

        nameField.setStyle("-fx-background-color: lightyellow;");
        surnameField.setStyle("-fx-background-color: lightyellow;");
        departmentField.setStyle("-fx-background-color: lightyellow;");
        numberField.setStyle("-fx-background-color: lightyellow;");
        searchField.setStyle("-fx-background-color: lightyellow;");

        // Layout
        HBox inputBox = new HBox(10, nameField, surnameField, departmentField, numberField, addButton, deleteButton, updateButton);
        HBox actionBox = new HBox(10, searchField, searchButton, sortButton);
        VBox layout = new VBox(10, inputBox, actionBox, tableView);
        layout.setPadding(new javafx.geometry.Insets(10));

        Image icon = new Image(getClass().getResourceAsStream("/icons/ikon.png"));
        primaryStage.getIcons().add(icon);
        // Scene
        Scene scene = new Scene(layout, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ogrenci Bilgi Sistemi");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
