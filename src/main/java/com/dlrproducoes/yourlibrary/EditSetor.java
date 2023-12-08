package com.dlrproducoes.yourlibrary;

import com.dlrproducoes.yourlibrary.model.tables.Setor;
import com.dlrproducoes.yourlibrary.utils.database.PoloDao;
import com.dlrproducoes.yourlibrary.utils.database.SetorDao;
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

public class EditSetor implements Initializable {
    private List<TextField> fields = new ArrayList<TextField>();
    private int ID;

    @FXML
    private Setor setorEdit;

    @FXML
    private TextField nome;

    @FXML
    private TextField polo;

    @FXML
    private TextField area;

    @FXML
    private Button updateButton;
    
    @FXML
    void setSetor(Setor setor){
        nome.setText(setor.getNome());
        polo.setText(setor.getPolo());
        area.setText(setor.getArea());
        ID = setor.getId();
        setorEdit = setor;
    }

    @FXML
    public void goBack(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaSetor.fxml"));
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
        if (validateCampos()){
            persistencePolo();
        } else  {
            System.out.println("erro ao fazer update");
        }
    }

    public void persistencePolo(){
        System.out.println(ID);
        Setor setor = new Setor(ID, polo.getText(), nome.getText(), area.getText());
        SetorDao setorDao = new SetorDao();
        setorDao.update(setor);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateButton.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Update();
            }
        });

        fields.add(nome);
        fields.add(area);
        fields.add(polo);
    }
}
