package com.fittrack.controller;

import java.time.LocalDate;
import java.util.List;

import com.fittrack.SceneManager;
import com.fittrack.SessionManager;
import com.fittrack.model.CalorieLogDAO;
import com.fittrack.model.Makanan;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

@SuppressWarnings("unused")
public class KaloriController {

    @FXML
    private DatePicker datePicker;
    
    @FXML
    private TextField namaMakananField;
    
    @FXML
    private TextField jumlahKaloriField;
    
    @FXML
    private TableView<Makanan> tabelMakanan;
    
    @FXML
    private TableColumn<Makanan, String> kolomMakanan;
    
    @FXML
    private TableColumn<Makanan, Integer> kolomKalori;
    
    @FXML
    private TableColumn<Makanan, Void> kolomAksi;
    
    @FXML
    private Label totalKaloriLabel;

    private final CalorieLogDAO calorieLogDAO = new CalorieLogDAO();
    private final ObservableList<Makanan> daftarMakanan = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tabelMakanan.setEditable(true);
        tabelMakanan.setItems(daftarMakanan);
        
        kolomMakanan.prefWidthProperty().bind(tabelMakanan.widthProperty().multiply(0.40));
        kolomKalori.prefWidthProperty().bind(tabelMakanan.widthProperty().multiply(0.25));
        kolomAksi.prefWidthProperty().bind(tabelMakanan.widthProperty().multiply(0.25));

        setupColumns();
        datePicker.setValue(LocalDate.now());
        muatDataLog();
    }
    
    private void setupColumns() {
        kolomMakanan.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNama()));
        kolomMakanan.setCellFactory(TextFieldTableCell.forTableColumn());
        kolomMakanan.setOnEditCommit(event -> {
            Makanan makanan = event.getRowValue();
            makanan.setNama(event.getNewValue());
            calorieLogDAO.updateLog(makanan);
            perbaruiTotalKalori();
        });

        kolomKalori.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKalori()));
        kolomKalori.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        kolomKalori.setOnEditCommit(event -> {
            Makanan makanan = event.getRowValue();
            if (event.getNewValue() != null) {
                makanan.setKalori(event.getNewValue());
                calorieLogDAO.updateLog(makanan);
                perbaruiTotalKalori();
            } else {
                tabelMakanan.refresh();
            }
        });

        Callback<TableColumn<Makanan, Void>, TableCell<Makanan, Void>> cellFactory = param -> new TableCell<>() {
            private final ImageView iconHapus = new ImageView(new Image(getClass().getResourceAsStream("/com/fittrack/images/delete_icon.png")));
            private final Button btnHapus = new Button("", iconHapus);
            {
                iconHapus.setFitHeight(20);
                iconHapus.setFitWidth(20);
                btnHapus.getStyleClass().add("icon-button");
                btnHapus.setOnAction((ActionEvent event) -> {
                    Makanan makanan = getTableView().getItems().get(getIndex());
                    calorieLogDAO.deleteLog(makanan.getLogId());
                    muatDataLog();
                });
            }
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnHapus);
                }
            }
        };

        kolomAksi.setCellFactory(cellFactory);
    }
    
    @FXML
    private void onDateChanged(ActionEvent event) {
        muatDataLog();
    }

    @FXML
    private void onTambahClick(ActionEvent event) {
        try {
            String nama = namaMakananField.getText();
            int kalori = Integer.parseInt(jumlahKaloriField.getText());
            if (nama.isEmpty() || kalori <= 0) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Input Tidak Valid", "Data tidak boleh kosong.");
                return;
            }

            Makanan makananBaru = new Makanan(nama, kalori);
            calorieLogDAO.addLog(SessionManager.getCurrentUserId(), makananBaru, datePicker.getValue());
            muatDataLog();
            
            namaMakananField.clear();
            jumlahKaloriField.clear();
        } catch (NumberFormatException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Input Salah", "Kalori harus berupa angka.");
        }
    }
    
    private void muatDataLog() {
        if (SessionManager.isLoggedIn() && datePicker.getValue() != null) {
            List<Makanan> logs = calorieLogDAO.getLogsForDate(SessionManager.getCurrentUserId(), datePicker.getValue());
            daftarMakanan.setAll(logs);
            perbaruiTotalKalori();
        }
    }

    private void perbaruiTotalKalori() {
        int total = daftarMakanan.stream().mapToInt(Makanan::getKalori).sum();
        if (SessionManager.isLoggedIn()){
            int target = SessionManager.getCurrentUser().getCalorieTarget();
            totalKaloriLabel.setText(String.format("%d / %d kkal", total, target));
            totalKaloriLabel.getStyleClass().remove("over-limit-label");
            if (total > target) {
                totalKaloriLabel.getStyleClass().add("over-limit-label");
            }
        } else {
            totalKaloriLabel.setText(String.valueOf(total));
        }
    }
    
    @FXML
    private void onKembaliClick(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/dashboard-view.fxml");
    }
}