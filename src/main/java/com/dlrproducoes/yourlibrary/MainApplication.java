package com.dlrproducoes.yourlibrary;

import com.dlrproducoes.yourlibrary.utils.database.Conexao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmMain = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene screenMain = new Scene(fxmMain.load(), 800, 800);


        stage.setTitle("Compositor de Materias!");
        stage.setScene(screenMain);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}