package com.dlrproducoes.yourlibrary;

import com.dlrproducoes.yourlibrary.model.tables.Polo;
import com.dlrproducoes.yourlibrary.utils.database.PoloDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreatePolo implements Initializable {

    private List<TextField> fields = new ArrayList<TextField>();

    @FXML
    private TextField nome;

    @FXML
    private TextField endereco;

    @FXML
    private Button buttonCreate;

    @FXML
    private Button buttonBack;

    @FXML
    protected void Create() {
        if (validateCampos()) {
            System.out.println(nome.getText() + " " + endereco.getText());
            exePersistence();
        } else {
            System.out.println("Nao tem nada nos campos");
        }
        editNotify();
        clearScreen();
    }

    private void editNotify() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Campos validos");
        alert.setHeaderText("Inclusao refletiu no banco");
        alert.setContentText("Agora pode continuar criando ou voltar!");
        alert.show();
    }

    private void exePersistence() {
        Polo polo = new Polo(0,nome.getText(), endereco.getText());
        PoloDao poloDao = new PoloDao();
        poloDao.save(polo);
    }

    private void clearScreen() {
        nome.clear();
        endereco.clear();
    }

    private boolean validateCampos(){
        try {
            for (TextField field : fields) {
                if (field.getText().isEmpty()) {
                    return false;
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos invalidos");
            alert.setHeaderText("Preencha todos os campos");
            alert.setContentText("Precisamos do preenchimento de todos os dados para funcionarmos!");
            alert.show();
        }
        return true;
    }

    @FXML
    public void goBack(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent home_page_parent = null;
        try {
            home_page_parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(home_page_parent, 800, 800);

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setWidth(800);
        app_stage.setHeight(800);
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCreate.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Create();
            }
        });

        fields.add(nome);
        fields.add(endereco);
    }
}
