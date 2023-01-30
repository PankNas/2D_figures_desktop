package com.gui;

import lombok.extern.slf4j.Slf4j;
import panknas.figs2D.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

@Slf4j
public class SquareFigController {
    @FXML
    private Button btnCancell;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    void onCancellBtnClick(ActionEvent event) {
        HelperFuncs.closeWindow(btnCancell);
    }

    @FXML
    void onCountBtnClick(ActionEvent event) {
        log.info("square button pressed");
        try {
            IShape fig = HelperFuncs.onCountBtnClick("Площадь посчитана", comboBox, btnCancell);
            MainController.value.add(fig.square());
            MainController.value.add(fig);
            log.info("square equals {}", fig.square());
        }
        catch (Exception ex) {
            log.warn("error: {}", ex.getMessage());
            HelperFuncs.failAlert(ex.getMessage());
        }
    }

    @FXML
    void initialize() {
        HelperFuncs.setComboBoxFigs(comboBox);
    }
}
