module com.dlrproducoes.yourlibrary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.dlrproducoes.yourlibrary to javafx.fxml;
    exports com.dlrproducoes.yourlibrary;
    exports com.dlrproducoes.yourlibrary.model.tables;
    opens com.dlrproducoes.yourlibrary.model.tables to javafx.fxml;
}