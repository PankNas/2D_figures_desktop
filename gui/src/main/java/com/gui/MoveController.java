package com.gui;

import lombok.extern.slf4j.Slf4j;
import panknas.figs2D.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Slf4j
public class MoveController {
    @FXML
    private ComboBox<String> comboFigs;
    @FXML
    private ComboBox<String> comboMove;
    @FXML
    private Button btnCancell;
    @FXML
    private AnchorPane pane;

    @FXML
    void onCancellBtnClick(ActionEvent event) {
        Stage stage = (Stage) btnCancell.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onMoveBtnClick(ActionEvent event) {
        log.info("move button pressed");
        try {
            int curFig = comboFigs.getSelectionModel().getSelectedIndex();
            if (curFig == -1)
                throw new Exception("Фигура не выбрана");
            int curMove = comboMove.getSelectionModel().getSelectedIndex();
            switch (curMove) {
                case 0 -> shftFig(curFig);
                case 1 -> rotateFig(curFig);
                case 2 -> symmetryFig(curFig);
                default -> throw new Exception("Не выбран тип движения");
            }
            pane.getChildren().clear();
            HelperFuncs.closeWindow(btnCancell);
            HelperFuncs.successAlert("Фигура успешно перемещена");
            log.info("fig move success");
        }
        catch (Exception ex) {
            HelperFuncs.failAlert(ex.getMessage());
            log.warn("error: {}", ex.getMessage());
        }
    }

    void shftFig(int index) throws Exception {
        log.info("type move figs is shift");
        Node vBox = pane.getChildren().get(0);
        double[] coords = HelperFuncs.getArray(vBox);
        log.info("shifting on vector {}", coords);
        IShape fig = HelloApplication.listFigures.get(index);
        fig.shift(new Point2D(coords));
    }

    void rotateFig(int index) throws Exception {
        log.info("type move figs is rotate");
        Node node = pane.getChildren().get(0);
        double phi = getPhi(node);
        IShape fig = HelloApplication.listFigures.get(index);
        fig.rot(phi);
    }

    public double getPhi(Node node) {
        double num = Float.POSITIVE_INFINITY;
        if (node instanceof HBox) {
            Node xText = ((HBox) node).getChildren().get(1);
            if (xText instanceof TextField) {
                num = Integer.parseInt(((TextField) xText).getText());
            }
        }
        log.info("phi move = {}", num);
        return num;
    }

    void symmetryFig(int index) throws Exception {
        log.info("type move figs is symmetryAxis");
        Node node = pane.getChildren().get(0);
        String axis = getAxis(node);
        IShape fig = HelloApplication.listFigures.get(index);
        switch (axis) {
            case "x" -> fig.symAxis(0);
            case "y" -> fig.symAxis(1);
            default -> throw new Exception("Ось симметрии введена неправильно");
        }

    }

    public String getAxis(Node node) {
        String axis = "";
        if (node instanceof HBox) {
            Node text = ((HBox) node).getChildren().get(1);
            if (text instanceof Spinner<?>) {
                axis = (String) ((Spinner<?>) text).getValue();
            }
        }
        log.info("Axis move = {}", axis);
        return axis;
    }

    @FXML
    void selectMove(ActionEvent event) {
        log.info("select type move play");
        pane.getChildren().clear();
        int curMove = comboMove.getSelectionModel().getSelectedIndex();
        switch (curMove) {
            case 0 -> {
                Label label = new Label("Вектор сдвига:");
                VBox vBox = HelperFuncs.createBox(label);
                pane.getChildren().add(vBox);
            }
            case 1 -> {
                TextField text = new TextField();
                setBox("Угол поворота:", text);
            }
            case 2 -> {
                Spinner<String> spinnerPoints = new Spinner<>();
                ObservableList<String> axis = FXCollections.observableArrayList("x", "y");
                SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(axis);
                valueFactory.setValue("x");
                spinnerPoints.setValueFactory(valueFactory);
                setBox("Ось симметрии:", spinnerPoints);
            }
        }
    }

    private void setBox(String str, Node field) {
        Label label = new Label(str);
        HBox hBox = new HBox(label, field);
        hBox.setSpacing(5);
        pane.getChildren().add(hBox);
    }

    @FXML
    void initialize() {
        ObservableList<String> listMove = FXCollections.observableArrayList("Сдвиг", "Поворот", "Симмерия");
        comboMove.setItems(listMove);

        HelperFuncs.setComboBoxFigs(comboFigs);
    }
}
