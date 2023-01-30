package com.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import panknas.figs2D.*;
import com.mongodb.*;

import java.util.Objects;

@Slf4j
public class AddFigureController{
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button buttonCancell;
    @FXML
    private HBox areaAmountPoints;
    @FXML
    private Spinner<Integer> spinnerPoints;
    private CanvasDrawer canvasDrawer;

    @FXML
    void selectFigure(ActionEvent event) throws Exception {
        String curFigure = comboBox.getSelectionModel().getSelectedItem();
        log.info("selected {}", curFigure);
        if (Objects.equals(curFigure, "Ломаная") || Objects.equals(curFigure, "Многоугольник")) {
            areaAmountPoints.setVisible(true);
            spinnerPoints.setValueFactory(valueFactory);
            log.info("selected shape is variable-sized");
        }
        else {
            log.info("selected shape is fixed-sized, delegating buttons to switch");
            areaAmountPoints.setVisible(false);
        }
        setN();
    }

    @FXML
    private VBox panePoints;
    private int n = 0; // количество точек в фигуре

    void createFieldsForPoints(String fig) {
        panePoints.getChildren().clear();
        for (int i = 0; i < n; i++) {
            Label label = new Label((i + 1) + " точка");
            VBox vBox = HelperFuncs.createBox(label);
            panePoints.getChildren().add(vBox);
        }
        if (Objects.equals(fig, "Окружность")) {
            Label r = new Label("радиус:");
            TextField text = new TextField();
            HBox hBox = new HBox(r, text);
            hBox.setPadding(new Insets(10, 0, 10, 0));
            hBox.setSpacing(5);
            panePoints.getChildren().add(hBox);
        }
    }

    void setN() throws Exception {
        try {
            String type = comboBox.getSelectionModel().getSelectedItem();
            if (type == null)
                throw new Exception("Не выбран тип фигуры");
            switch (type) {
                case "Окружность" -> n = 1;
                case "Отрезок" -> n = 2;
                case "Треугольник" -> n = 3;
                case "Четырёхугольник", "Прямоугольник", "Трапеция" -> n = 4;
                case "Ломаная", "Многоугольник" -> spinnerPoints.valueProperty().addListener(new ChangeListener<Integer>() {
                    @Override
                    public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                        n = spinnerPoints.getValue();
                        createFieldsForPoints(comboBox.getSelectionModel().getSelectedItem());
                    }
                });
            }
            createFieldsForPoints(comboBox.getSelectionModel().getSelectedItem());
        }
        catch (Exception ex) {
            HelperFuncs.failAlert(ex.getMessage());
        }
    }

    @FXML
    void onAddBtnClick(ActionEvent event) throws Exception {
        log.info("add button pressed");
        try {
            Point2D[] points = new Point2D[n];
            if (n <= 0)
                throw new Exception("Кол.-во точек неверное!");
            for (int i = 0; i < n; i++) {
                Node pane = panePoints.getChildren().get(i);
                double[] coords = HelperFuncs.getArray(pane);
                if (coords[0] == Float.POSITIVE_INFINITY || coords[1] == Float.POSITIVE_INFINITY)
                    throw new Exception("Не все координаты корректно заполнены!");
                else {
                    points[i] = new Point2D(coords);
                }
            }

            createFigure(points);
            createFieldsForPoints(comboBox.getSelectionModel().getSelectedItem());
            HelperFuncs.closeWindow(buttonCancell);
            HelperFuncs.successAlert("Фигура успешно создана");
        }
        catch (Exception e) {
            HelperFuncs.failAlert(e.getMessage());
        }
    }

    void createFigure(Point2D[] points) throws Exception {
        int type = comboBox.getSelectionModel().getSelectedIndex();
        IShape figure;
        switch (type) {
            case 0 -> figure = new Segment(points);
            case 1 -> figure = new Polyline(points);
            case 2 -> figure = new NGon(points, String.valueOf(NGon.class));
            case 3 -> figure = new TGon(points);
            case 4 -> figure = new QGon(points);
            case 5 -> figure = new Rectangle(points);
            case 6 -> figure = new Trapeze(points);
            case 7 -> {
                Node pane = panePoints.getChildren().get(1);
                double r = 0;
                if (pane instanceof HBox) {
                    Node rText = ((HBox) pane).getChildren().get(1);
                    if (rText instanceof TextField)
                        r = Integer.parseInt(((TextField) rText).getText());
                }
                if (r > 0)
                    figure = new Circle(points[0], r);
                else
                    throw new Exception("Неверный радиус окружности");
            }
            default -> throw new Exception("Не выбран тип фигуры");
        }
        HelloApplication.listFigures.add(figure);
    }

    @FXML
    void onCancellBtnClick(ActionEvent event) {
        log.info("cancell button pressed");
        HelperFuncs.closeWindow(buttonCancell);
    }

    SpinnerValueFactory<Integer> valueFactory;
    @FXML
    void initialize() {
        HelperFuncs.setComboBoxTypeFigs(comboBox);
        panePoints.setPadding(new Insets(0, 0, 5, 10));
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        canvasDrawer = MainController.canvasDrawer;
    }
}
