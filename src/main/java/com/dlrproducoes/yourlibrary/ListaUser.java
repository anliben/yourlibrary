package com.dlrproducoes.yourlibrary;

import com.dlrproducoes.yourlibrary.model.tables.Tarefa;
import com.dlrproducoes.yourlibrary.model.tables.User;
import com.dlrproducoes.yourlibrary.utils.database.TarefaDao;
import com.dlrproducoes.yourlibrary.utils.database.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListaUser implements Initializable {
    @FXML
    TableView<User> tableView;

    String setorHandleVar;

    private void goToEdit(User user, ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditUser.fxml"));
        Parent home_page_parent = null;

        try {
            home_page_parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EditUser userController = loader.getController();
        userController.setUser(user);

        Scene scene = new Scene(home_page_parent, 320, 240);

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }

    private void delete(User user) {
        UserDao userDao = new UserDao();
        userDao.delete(user);
    }

    @FXML
    protected void goToNew(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateUser.fxml"));
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

        Scene scene = new Scene(home_page_parent, 320, 300);

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }

    @FXML
    protected void onHelloButtonClick(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaSetor.fxml"));
            Parent home_page_parent = loader.load();

            Scene scene = new Scene(home_page_parent, 800, 800);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            ListaSetor setorController = loader.getController();
            setorController.setPoloHandler(setorHandleVar);

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
    public void setSetorHandler(String setor) {
        UserDao userDao = new UserDao();
        List<User> userList = userDao.consultBySetor(setor);
        setorHandleVar = setor;

        TableColumn<User,String> ID = new TableColumn<>("Id");
        TableColumn<User, String> Nome = new TableColumn<>("Nome");
        TableColumn<User, String> Cpf = new TableColumn<>("Cpf");
        TableColumn<User, String> Email = new TableColumn<>("Email");
        TableColumn<User, String> Setor = new TableColumn<>("Setor");
        TableColumn<User, Void> editarColumn = new TableColumn<>("Editar");
        TableColumn<User, Void> removerColumn = new TableColumn<>("Remover");
        TableColumn<User, Void> AdicionarTarefa = new TableColumn<>("Adicionar Tarefa");
        TableColumn<User, Void> VerTarefa = new TableColumn<>("Ver Tarefa");

        tableView.getColumns().addAll(
                ID,
                Nome,
                Cpf,
                Email,
                Setor,
                AdicionarTarefa,
                VerTarefa,
                editarColumn,
                removerColumn
        );

        tableView.getItems().addAll(userList);
        tableView.refresh();

        ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        Cpf.setCellValueFactory(new PropertyValueFactory<>("Cpf"));
        Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        Setor.setCellValueFactory(new PropertyValueFactory<>("Setor"));

        AdicionarTarefa.setCellFactory(param -> new TableCell<>() {
            private final Button editarButton = new Button("Adicionar Tarefa");
            {
                editarButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    Dialog dialog = new Dialog();
                    dialog.setTitle("Adicionar Tarefa");
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                    dialog.getDialogPane().setContent(criarTarefa(user));
                    dialog.show();
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

        VerTarefa.setCellFactory(param -> new TableCell<>() {
            private final Button editarButton = new Button("Ver Tarefa");
            {
                editarButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    Dialog dialog = new Dialog();
                    dialog.setTitle("Ver Tarefa");
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                    dialog.getDialogPane().setContent(listarTarefa(user));
                    dialog.show();
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

        editarColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editarButton = new Button("Editar");
            {
                editarButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    // Lógica para editar o polo
                    System.out.println("Editar: " + user.getNome());
                    goToEdit(user, event);
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
                    User user = getTableView().getItems().get(getIndex());
                    // Lógica para remover o polo
                    System.out.println("Remover: " + user.getNome());
                    delete(user);
                    getTableView().getItems().remove(user);
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

    private Node criarTarefa(User user) {
        GridPane gridPane = new GridPane();
        TextField nome = new TextField();
        TextField descricao = new TextField();

        Button button = new Button("Adicionar");
        button.setOnMouseClicked(e -> {

            TarefaDao tarefaDao = new TarefaDao();
            Tarefa tarefa = new Tarefa(0, user.getId(), nome.getText(), descricao.getText());

            tarefaDao.save(tarefa);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Campos validos");
                alert.setHeaderText("Inclusao refletiu no banco");
                alert.setContentText("Agora pode continuar criando ou voltar!");
                alert.show();
        });

        gridPane.add(new Label("Nome"), 0,0);
        gridPane.add(new Label("Descricao"), 0,1);
        gridPane.add(nome, 1,0);
        gridPane.add(descricao, 1,1);
        gridPane.add(button, 0, 2);

        return gridPane;
    }

    private Node listarTarefa(User user) {
        GridPane gridPane = new GridPane();
        TarefaDao tarefaDao = new TarefaDao();
        ListView<Object> listView = new ListView<>();

        List<Tarefa> tarefas = tarefaDao.consultAllByID(user.getId());
        tarefas.forEach(item -> {
            listView.getItems().addAll(item.getNome());
        });
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        gridPane.add(listView, 0, 0);

        return gridPane;

    }
}
