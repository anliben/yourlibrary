package com.dlrproducoes.yourlibrary;

import com.dlrproducoes.yourlibrary.model.tables.Setor;
import com.dlrproducoes.yourlibrary.utils.database.SetorDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class ListaSetor implements Initializable {

    List<Setor> setorList;
    private String poloHandler;

    @FXML
    TableView<Setor> tableView;

    private void goToEdit(Setor setor, ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditSetor.fxml"));
        Parent home_page_parent = null;

        try {
            home_page_parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EditSetor setorController = loader.getController();
        setorController.setSetor(setor);

        Scene scene = new Scene(home_page_parent, 320, 240);

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }

    private void delete(Setor setor) {
        SetorDao setorDao = new SetorDao();
        setorDao.delete(setor);
    }

    @FXML
    protected void goToNew(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateSetor.fxml"));
            Parent home_page_parent = loader.load();

            Scene scene = new Scene(home_page_parent, 320, 240);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(scene);
            app_stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo FXML", e);
        } catch (Exception e) {
            // Desempacote a exceção real e imprima a stack trace para depuração
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
            throw new RuntimeException("Erro durante a inicialização da tela", e);
        }
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

        Scene scene = new Scene(home_page_parent, 600, 600);

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setWidth(600);
        app_stage.setHeight(600);
        app_stage.setTitle("Compositor de materiais");
        app_stage.setScene(scene);
        app_stage.show();
    }

    @FXML
    protected void onHelloButtonClick(ActionEvent event) {
        try {
            String poloSelected = tableView.getSelectionModel().getSelectedItems().getFirst().getNome();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaUser.fxml"));
            Parent home_page_parent = loader.load();


            Scene scene = new Scene(home_page_parent, 800, 800);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            ListaUser userLista = loader.getController();
            userLista.setSetorHandler(poloSelected);

            app_stage.hide();
            app_stage.setTitle("Lista de Colaboradores");
            app_stage.setWidth(800);
            app_stage.setHeight(800);
            app_stage.setScene(scene);
            app_stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Colaborador selecionado");
            alert.setHeaderText("Selecionar colaborador");
            alert.setContentText("Voce deve selecionar algum colaborador!");
            alert.show();
        }
    }

    @FXML
    public void setPoloHandler(String polo) {
        SetorDao setorDao = new SetorDao();
        setorList = setorDao.consultAllByName(polo);

        TableColumn<Setor,String> ID = new TableColumn<>("Id");
        TableColumn<Setor, String> Nome = new TableColumn<>("Nome");
        TableColumn<Setor, String> Polo = new TableColumn<>("Polo");
        TableColumn<Setor, String> Area = new TableColumn<>("Area");
        TableColumn<Setor, Void> editarColumn = new TableColumn<>("Editar");
        TableColumn<Setor, Void> removerColumn = new TableColumn<>("Remover");

        tableView.getColumns().addAll(
                ID,
                Nome,
                Area,
                Polo,
                editarColumn,
                removerColumn
        );

        tableView.getItems().addAll(setorList);
        tableView.refresh();

        ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        Polo.setCellValueFactory(new PropertyValueFactory<>("Polo"));
        Area.setCellValueFactory(new PropertyValueFactory<>("Area"));

        editarColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editarButton = new Button("Editar");
            {
                editarButton.setOnAction(event -> {
                    Setor setor = getTableView().getItems().get(getIndex());
                    goToEdit(setor, event);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editarButton);
                }
            }
        });

        removerColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removerButton = new Button("Remover");

            {
                removerButton.setOnAction(event -> {
                    Setor setor = getTableView().getItems().get(getIndex());
                    delete(setor);
                    getTableView().getItems().remove(setor);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removerButton);
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
