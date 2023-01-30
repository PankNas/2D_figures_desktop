package com.gui;

import lombok.extern.slf4j.Slf4j;
import panknas.figs2D.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.Objects;

@Slf4j
public class CrossFigsController {
    @FXML
    private Button btnCancell;

    @FXML
    private ComboBox<IShape> fig1;

    @FXML
    private ComboBox<IShape> fig2;

    @FXML
    private ComboBox<String> typeFig;

    @FXML
    void onCancellBtnClick(ActionEvent event) {
        log.info("cancell button pressed");
        HelperFuncs.closeWindow(btnCancell);
    }

    public static IShape getFigure(ComboBox<IShape> comboBox) throws Exception {
        int curFigIndex = comboBox.getSelectionModel().getSelectedIndex();
        if (curFigIndex == -1) {
            log.warn("shape not chosen");
            throw new Exception("Фигура не выбрана");
        }
        return HelloApplication.listFigures.get(curFigIndex);
    }

    @FXML
    void onCountBtnClick(ActionEvent event) {
        try {
            IShape f1 = getFigure(fig1);
            IShape f2 = getFigure(fig2);

            MainController.value.add(f1.cross(f2));
            MainController.value.add(f1);
            MainController.value.add(f2);

            HelperFuncs.closeWindow(btnCancell);
            HelperFuncs.successAlert("Пересечение определено");
            log.info("intersection defined: {}", f1.cross(f2));
        }
        catch (Exception ex) {
            log.warn("intersecting uncompatible shapes");
            HelperFuncs.failAlert(ex.getMessage());
        }
    }

    ObservableList<IShape> sameTypeFigs;
    @FXML
    void selectFig(ActionEvent event) {
        sameTypeFigs.clear();
        String curTypeFig = typeFig.getSelectionModel().getSelectedItem();
        for (IShape fig : HelloApplication.listFigures) {
            if (Objects.equals(fig.getNameFig(1), curTypeFig)) {
                sameTypeFigs.add(fig);
            }
        }
        fig1.setItems(sameTypeFigs);
        fig2.setItems(sameTypeFigs);
    }

    @FXML
    void initialize() {
        HelperFuncs.setComboBoxTypeFigs(typeFig);
        sameTypeFigs = FXCollections.observableArrayList();
    }
}
