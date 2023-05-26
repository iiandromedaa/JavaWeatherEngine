package com.androbohij;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class OfflineHandler {
    @FXML
    WindowHandler windo;

    @FXML
    void goRefresh(ActionEvent event) throws IOException {
        App.jwel.refresh();
        windo.fresh();
    }

    public void setMainTestController(WindowHandler windowHandler) {
        this.windo = windowHandler;
    }
}
