package com.gui;

import panknas.figs2D.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelperFuncs {
    public static double[] getArray(Node pane) {
        double x = Float.POSITIVE_INFINITY;
        double y = Float.POSITIVE_INFINITY;
        if (pane instanceof VBox) {
            Node coords = ((VBox) pane).getChildren().get(1);
            if (coords instanceof HBox) {
                Node xText = ((HBox) coords).getChildren().get(1);
                Node yText = ((HBox) coords).getChildren().get(3);
                if (xText instanceof TextField) {
                    x = Integer.parseInt(((TextField) xText).getText());
                    y = Integer.parseInt(((TextField) yText).getText());
                }
            }
        }
        return new double[] {x, y};
    }

    public static VBox createBox(Label label) {
        Label X = new Label("x:");
        TextField textX = new TextField();
        Label Y = new Label("y:");
        TextField textY = new TextField();

        HBox hBox = new HBox(X, textX, Y, textY);
        hBox.setSpacing(5);

        return new VBox(label, hBox);
    }

    public static void successAlert(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText(str);
        alert.setContentText("");
        alert.showAndWait();
    }

    public static void failAlert(String str) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Возникла следующая ошибка:");
        alert.setContentText(str);
        alert.showAndWait();
    }

    public static void setComboBoxFigs(ComboBox<String> box) {
        ObservableList<String> listFigs = FXCollections.observableArrayList();
        for (IShape fig : HelloApplication.listFigures) {
            listFigs.add(fig.toString());
        }
        box.setItems(listFigs);
    }

    public static void closeWindow(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public static IShape onCountBtnClick(String str, ComboBox<String> comboBox, Button btn) throws Exception {
        int curFigIndex = comboBox.getSelectionModel().getSelectedIndex();
        if (curFigIndex == -1)
            throw new Exception("Фигура не выбрана");
        HelperFuncs.closeWindow(btn);
        HelperFuncs.successAlert(str);
        return HelloApplication.listFigures.get(curFigIndex);
    }

    public static void setComboBoxTypeFigs(ComboBox<String> comboBox) {
        ObservableList<String> list = FXCollections.observableArrayList(
                "Отрезок", "Ломаная", "Многоугольник", "Треугольник",
                "Четырёхугольник", "Прямоугольник", "Трапеция", "Окружность"
        );
        comboBox.setItems(list);
    }
}
