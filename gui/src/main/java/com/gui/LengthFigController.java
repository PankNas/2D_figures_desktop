package com.gui;

import lombok.extern.slf4j.Slf4j;
import panknas.figs2D.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

@Slf4j
public class LengthFigController {
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
        log.info("count button pressed");
        try {
            IShape fig = HelperFuncs.onCountBtnClick("Периметр посчитан", comboBox, btnCancell);
            MainController.value.add(fig.length());
            MainController.value.add(fig);
            log.info("area equals {}", fig.length());
        }
        catch (Exception ex) {
            log.warn("figure not selected");
            HelperFuncs.failAlert(ex.getMessage());
        }
    }

    @FXML
    void initialize() {
        HelperFuncs.setComboBoxFigs(comboBox);
    }
}
