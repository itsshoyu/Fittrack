package com.fittrack.controller;

import java.util.List;

import com.fittrack.SceneManager;
import com.fittrack.model.Latihan;
import com.fittrack.model.LatihanData;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DaftarLatihanController {

    @FXML
    private Label judulKategoriLabel;
    
    @FXML
    private ListView<Latihan> listViewLatihan;

    private static String kategoriTerpilih;

    public static void setKategori(String kategori) {
        kategoriTerpilih = kategori;
    }

    @FXML
    public void initialize() {
        if (kategoriTerpilih != null) {
            judulKategoriLabel.setText("Latihan " + kategoriTerpilih);
            List<Latihan> daftarLatihan = LatihanData.getLatihanForKategori(kategoriTerpilih);
            listViewLatihan.setItems(FXCollections.observableArrayList(daftarLatihan));

            listViewLatihan.setCellFactory(param -> new ListCell<>() {
                private final ImageView imageView = new ImageView();
                private final Label namaLabel = new Label();
                private final Label descLabel = new Label();
                private final VBox textVBox = new VBox(5, namaLabel, descLabel);
                private final HBox hBox = new HBox(15, imageView, textVBox);

                {
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    namaLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
                    descLabel.setWrapText(true);
                    descLabel.prefWidthProperty().bind(listViewLatihan.widthProperty().subtract(120));
                    imageView.setFitHeight(80);
                    imageView.setFitWidth(80);
                }

                @Override
                protected void updateItem(Latihan latihan, boolean empty) {
                    super.updateItem(latihan, empty);
                    if (empty || latihan == null) {
                        setGraphic(null);
                    } else {
                        // Set data untuk setiap komponen
                        namaLabel.setText(latihan.getNama());
                        descLabel.setText(latihan.getDeskripsi());
                        
                        // Coba muat gambar, tangani jika error
                        try {
                            Image image = new Image(getClass().getResourceAsStream("/com/fittrack/" + latihan.getImagePath()));
                            imageView.setImage(image);
                        } catch (Exception e) {
                            // Jika gambar tidak ditemukan, set ke null atau gambar placeholder
                            imageView.setImage(null); 
                            System.err.println("Gagal memuat gambar: " + latihan.getImagePath());
                        }
                        
                        setGraphic(hBox);
                    }
                }
            });
        }
    }

    @FXML
    void kembaliKePanduan(ActionEvent event) {
        SceneManager.switchScene(event, 
        "/com/fittrack/panduan-latihan-view.fxml");
    }
}