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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditPolo implements Initializable {
    private List<TextField> fields = new ArrayList<TextField>();
    private int ID;

    @FXML
    private Polo poloEdit;

    @FXML
    private TextField nome;

    @FXML
    private TextField endereco;

    @FXML
    private Button updateButton;
    public void setPolo(Polo polo) {
        nome.setText(polo.getNome());
        endereco.setText(polo.getEndereco());
        ID = polo.getId();
        poloEdit = polo;
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

        Scene scene = new Scene(home_page_parent, 320, 300);

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }

    public void Update(){
        try {
            if (validateCampos()){
                persistencePolo();
            } else  {
                System.out.println("erro ao fazer update");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos invalidos");
            alert.setHeaderText("Preencha todos os campos");
            alert.setContentText("Precisamos do preenchimento de todos os dados para funcionarmos!");
            alert.show();
        }

    }

    public void persistencePolo(){
        System.out.println(ID);
        Polo polo = new Polo(ID, nome.getText(), endereco.getText());
        PoloDao poloDao = new PoloDao();
        poloDao.update(polo);
        editNotify();
    }

    private void editNotify() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Campos validos");
        alert.setHeaderText("Alteracao ja refletiu no banco");
        alert.setContentText("Agora pode continuar editando ou voltar!");
        alert.show();
    }

    private boolean validateCampos(){
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateButton.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Update();
            }
        });

        fields.add(nome);
        fields.add(endereco);
    }
}
