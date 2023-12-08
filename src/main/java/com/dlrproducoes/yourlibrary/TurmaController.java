package com.dlrproducoes.yourlibrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TurmaController implements Initializable {
    @FXML
    private Text Nome;

    public void setNome(String nome) {
        System.out.println("params: "+ nome);
    }

    public String getNome() { return Nome.getText(); }

    @FXML
    protected void onHelloButtonClick2(ActionEvent event) {

        Parent home_page_parent = null;
        try {
            home_page_parent = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene home_page_scene = new Scene(home_page_parent, 320, 240);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //        System.out.println("Iniciei uma tela de turma: " + Nome);

    }
}
