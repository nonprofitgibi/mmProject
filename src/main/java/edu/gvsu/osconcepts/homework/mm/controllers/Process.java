package edu.gvsu.osconcepts.homework.mm.controllers;

import edu.gvsu.osconcepts.homework.mm.Page;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Process {
    private ObservableList<PageWrapper> pages = FXCollections.observableArrayList();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblProcessId"
    private Label lblProcessId; // Value injected by FXMLLoader

    @FXML // fx:id="tblPages"
    private TableView<PageWrapper> tblPages; // Value injected by FXMLLoader

    @FXML // fx:id="tcPageId"
    private TableColumn<PageWrapper, Integer> tcPageId; // Value injected by FXMLLoader

    @FXML // fx:id="tcType"
    private TableColumn<PageWrapper, String> tcType; // Value injected by FXMLLoader

    @FXML // fx:id="tcFrameID"
    private TableColumn<PageWrapper, Integer> tcFrameID; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert lblProcessId != null : "fx:id=\"lblProcessId\" was not injected: check your FXML file 'Process.fxml'.";
        assert tblPages != null : "fx:id=\"tblPages\" was not injected: check your FXML file 'Process.fxml'.";
        assert tcPageId != null : "fx:id=\"tcPageId\" was not injected: check your FXML file 'Process.fxml'.";
        assert tcType != null : "fx:id=\"tcType\" was not injected: check your FXML file 'Process.fxml'.";
        assert tcFrameID != null : "fx:id=\"tcFrameID\" was not injected: check your FXML file 'Process.fxml'.";

        lblProcessId.setText(String.format("Process ID: %d", Main.newest.getId()));
        lblProcessId.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s",
                Main.colorToHex(Main.newest.getColor()), Main.colorToInverseHex(Main.newest.getColor())));

        for (Page p : Main.newest.getTextTable()) {
            pages.add(new PageWrapper(p, "Text"));
        }

        for (Page p : Main.newest.getDataTable()) {
            pages.add(new PageWrapper(p, "Data"));
        }
        tcPageId.setCellValueFactory(new PropertyValueFactory<PageWrapper, Integer>("pageId"));
        tcFrameID.setCellValueFactory(new PropertyValueFactory<PageWrapper, Integer>("frameId"));
        tcType.setCellValueFactory(new PropertyValueFactory<PageWrapper, String>("type"));
        tblPages.setItems(pages);
    }

    public static class PageWrapper {
        private final SimpleIntegerProperty pageId;
        private final SimpleStringProperty type;
        private final SimpleIntegerProperty frameId;

        private PageWrapper(Page p, String fType) {
            pageId = new SimpleIntegerProperty(p.getId());
            type = new SimpleStringProperty(fType);
            frameId = new SimpleIntegerProperty(p.getFrame());
        }

        public Integer getPageId() {
            return pageId.get();
        }

        public void setPageId(Integer id) {
            pageId.set(id);
        }

        public String getType() {
            return type.get();
        }

        public void setType(String fType) {
            type.set(fType);
        }

        public Integer getFrameId() {
            return frameId.get();
        }

        public void setFrameId(Integer id) {
            frameId.set(id);
        }
    }
}
