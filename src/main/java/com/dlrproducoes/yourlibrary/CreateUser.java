package com.dlrproducoes.yourlibrary;

import com.dlrproducoes.yourlibrary.model.tables.Polo;
import com.dlrproducoes.yourlibrary.model.tables.Setor;
import com.dlrproducoes.yourlibrary.model.tables.User;
import com.dlrproducoes.yourlibrary.utils.database.PoloDao;
import com.dlrproducoes.yourlibrary.utils.database.SetorDao;
import com.dlrproducoes.yourlibrary.utils.database.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateUser implements Initializable {

    private List<TextField> fields = new ArrayList<TextField>();

    @FXML
    private TextField nome;

    @FXML
    private TextField cpf;

    @FXML
    private TextField email;

    private String setor;

    @FXML
    private Button buttonCreate;

    @FXML
    public ChoiceBox<String> choiceSetor;


    @FXML
    private Button buttonBack;

    @FXML
    protected void Create() {
        if (validateCampos()) {
            System.out.println(nome.getText());
            exePersistence();
        } else {
            System.out.println("Nao tem nada nos campos");
        }

        clearScreen();
    }

    private void getSetor(ActionEvent event){
        setor = choiceSetor.getValue();
    }

    private void exePersistence() {
        User user = new User(
            0,
            nome.getText(),
                Long.parseLong(cpf.getText()),
            email.getText(),
                setor
        );
        UserDao userDao = new UserDao();
        userDao.save(user);
        editNotify();
    }

    private void editNotify() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Campos validos");
        alert.setHeaderText("Inclusao refletiu no banco");
        alert.setContentText("Agora pode continuar criando ou voltar!");
        alert.show();
    }

    private void clearScreen() {
        nome.clear();
        cpf.clear();
        email.clear();
    }

    private boolean validateCampos(){
        try {
            for (TextField field : fields) {
                if (field.getText().isEmpty()) {
                    return false;
                }
            }
            if (setor.isEmpty()) {
                return false;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaUser.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCreate.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Create();
            }
        });

        SetorDao setorDao = new SetorDao();
        List<Setor> setorList = setorDao.consultAll();

        setorList.forEach(item -> {
            choiceSetor.getItems().addAll(item.getNome());
            choiceSetor.setOnAction(this::getSetor);
        });

        fields.add(nome);
        fields.add(cpf);
        fields.add(email);
    }
}
