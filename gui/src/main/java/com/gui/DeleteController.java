package com.gui;

import lombok.extern.slf4j.Slf4j;
import panknas.figs2D.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

@Slf4j
public class DeleteController {
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button btnCancell;

    @FXML
    void onCancellBtnClick(ActionEvent event) {
        log.info("cancell in DeleteController button pressed");
        HelperFuncs.closeWindow(btnCancell);
    }

    @FXML
    void onDeleteBtnClick(ActionEvent event) {
        log.info("delete button pressed");
        try {
            int curFigIndex = comboBox.getSelectionModel().getSelectedIndex();
            if (curFigIndex == -1)
                throw new Exception("Фигура не выбрана");
            log.info("deleting {}", HelloApplication.listFigures.get(curFigIndex));
            HelloApplication.listFigures.remove(curFigIndex);
            HelperFuncs.successAlert("Фигура успешно удалена!");
            HelperFuncs.closeWindow(btnCancell);
        }
        catch (Exception ex) {
            HelperFuncs.failAlert(ex.getMessage());
        }
    }

    @FXML
    void initialize() {
        HelperFuncs.setComboBoxFigs(comboBox);
    }
}
