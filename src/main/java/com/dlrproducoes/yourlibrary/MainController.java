package com.dlrproducoes.yourlibrary;

import com.dlrproducoes.yourlibrary.model.tables.Polo;
import com.dlrproducoes.yourlibrary.utils.database.PoloDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private String poloSelected;
    private ObservableList<Polo> observableList = FXCollections.observableArrayList();

    @FXML
    private Label welcomeText;

    @FXML
    TableView<Polo> tableView;

    @FXML
    private ImageView imageView;

    @FXML
    protected void goToNew(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreatePolo.fxml"));
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
    protected void onHelloButtonClick(ActionEvent event) {
        String poloSelected = tableView.getSelectionModel().getSelectedItems().getFirst().getNome();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaSetor.fxml"));
            Parent home_page_parent = loader.load();

            Scene scene = new Scene(home_page_parent, 600, 600);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            ListaSetor setorController = loader.getController();
            setorController.setPoloHandler(poloSelected);

            app_stage.hide();
            app_stage.setScene(scene);
            app_stage.setWidth(600);
            app_stage.setHeight(600);
            app_stage.setTitle("Lista de Setores");


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

    private void goToEdit(Polo polo, ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPolo.fxml"));
        Parent home_page_parent = null;

        try {
            home_page_parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EditPolo setorController = loader.getController();
        setorController.setPolo(polo);

        Scene scene = new Scene(home_page_parent, 800, 800);

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("Editar");
        app_stage.setWidth(800);
        app_stage.setHeight(800);
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }

    @FXML
    private void mostrarSobre(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sobre");
        alert.setHeaderText("Saiba mais sobre nosso produto");
        alert.setContentText("O SuperAuto é um gerenciador de tarefas empresariais que tem como objetivo facilitar a administração das rotinas diárias da empresa e organizá-laz para melhor aproveitamento do tempo de serviço.");
        alert.show();
    }

    private void delete(Polo polo) {
        PoloDao poloDao = new PoloDao();
        poloDao.delete(polo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Iniciei uma tela de polo");

        PoloDao poloDao = new PoloDao();
        List<Polo> polosList = poloDao.consultAll();

        TableColumn<Polo,String> ID = new TableColumn<>("Id");
        TableColumn<Polo, String> Nome = new TableColumn<>("Nome");
        TableColumn<Polo, String> Endereco = new TableColumn<>("Endereco");
        TableColumn<Polo, Void> editarColumn = new TableColumn<>("Editar");
        TableColumn<Polo, Void> removerColumn = new TableColumn<>("Remover");

        tableView.getColumns().addAll(
                ID,
                Nome,
                Endereco,
                editarColumn,
                removerColumn
        );

        tableView.getItems().addAll(polosList);
        tableView.refresh();

        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));


        editarColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editarButton = new Button("Editar");
            {
                editarButton.setOnAction(event -> {
                    Polo polo = getTableView().getItems().get(getIndex());
                    // Lógica para editar o polo
                    System.out.println("Editar: " + polo.getNome());
                    goToEdit(polo, event);
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
                    Polo polo = getTableView().getItems().get(getIndex());
                    // Lógica para remover o polo
                    System.out.println("Remover: " + polo.getNome());
                    delete(polo);
                    getTableView().getItems().remove(polo);
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
}
