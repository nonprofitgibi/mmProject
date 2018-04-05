package edu.gvsu.osconcepts.homework.mm.controllers;

import edu.gvsu.osconcepts.homework.mm.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Main {
    public static Program newest;

    private ArrayList<Label> labels = new ArrayList<>();
    private Map<Integer, Parent> pages = new HashMap<>();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Label lbl0;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;

    @FXML
    private Label lbl5;

    @FXML
    private Label lbl6;

    @FXML
    private Label lbl7;

    @FXML // fx:id="btnPrevious"
    private Button btnPrevious; // Value injected by FXMLLoader

    @FXML // fx:id="btnNext"
    private Button btnNext; // Value injected by FXMLLoader

    @FXML // fx:id="fpPrograms"
    private FlowPane fpPrograms; // Value injected by FXMLLoader

    public static String colorToHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public static String colorToInverseHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getBlue() == 1 ? 0 : color.getBlue() * 255),
                (int) (color.getRed() == 1 ? 0 : color.getRed() * 255),
                (int) (color.getGreen() == 1 ? 0 : color.getGreen() * 255));
    }

    @FXML
    void onLoad(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select trace tape");
        File file = fileChooser.showOpenDialog(FxApplication.primaryStage);
        if (file != null) {
            TraceTape.INSTANCE.load(file);
            btnNext.disableProperty().setValue(false);
        }
    }

    @FXML
    void onNext(ActionEvent event) throws IOException {
        Event traceEvent = TraceTape.INSTANCE.nextElement();
        newest = MemoryManager.INSTANCE.processEvent(traceEvent);
        if (newest.getAllocated()) {
            Parent root = FXMLLoader.load(getClass().getResource("../Process.fxml"));
            pages.put(newest.getId(), root);
            fpPrograms.getChildren().add(root);
        } else {
            fpPrograms.getChildren().remove(pages.get(newest.getId()));
        }
        redrawFrames();
        if (!TraceTape.INSTANCE.hasMoreElements()) {
            btnNext.disableProperty().setValue(true);
        } else {
            btnNext.disableProperty().setValue(false);
        }
    }

    @FXML
    void onPrevious(ActionEvent event) {
        //TODO: Make this roll back the previous action
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert lbl0 != null : "fx:id=\"lbl0\" was not injected: check your FXML file 'Main.fxml'.";
        assert lbl1 != null : "fx:id=\"lbl1\" was not injected: check your FXML file 'Main.fxml'.";
        assert lbl2 != null : "fx:id=\"lbl2\" was not injected: check your FXML file 'Main.fxml'.";
        assert lbl3 != null : "fx:id=\"lbl3\" was not injected: check your FXML file 'Main.fxml'.";
        assert lbl4 != null : "fx:id=\"lbl4\" was not injected: check your FXML file 'Main.fxml'.";
        assert lbl5 != null : "fx:id=\"lbl5\" was not injected: check your FXML file 'Main.fxml'.";
        assert lbl6 != null : "fx:id=\"lbl6\" was not injected: check your FXML file 'Main.fxml'.";
        assert lbl7 != null : "fx:id=\"lbl7\" was not injected: check your FXML file 'Main.fxml'.";
        assert btnPrevious != null : "fx:id=\"btnPrevious\" was not injected: check your FXML file 'Main.fxml'.";
        assert btnNext != null : "fx:id=\"btnNext\" was not injected: check your FXML file 'Main.fxml'.";
        assert fpPrograms != null : "fx:id=\"fpPrograms\" was not injected: check your FXML file 'Main.fxml'.";

        labels.add(lbl0);
        labels.add(lbl1);
        labels.add(lbl2);
        labels.add(lbl3);
        labels.add(lbl4);
        labels.add(lbl5);
        labels.add(lbl6);
        labels.add(lbl7);

        redrawFrames();
        btnNext.disableProperty().setValue(true);
    }

    private void redrawFrames() {
        for (int i = 0; i < labels.size(); i++) {
            Frame f = MemoryManager.INSTANCE.getFrames().get(i);
            Label l = labels.get(i);
            l.setText(f.getText());
            // High contrast color from background to text color
            l.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s", colorToHex(f.getColor()),
                    colorToInverseHex(f.getColor())));
        }
    }
}
