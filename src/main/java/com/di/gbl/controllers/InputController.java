package com.di.gbl.controllers;

import com.di.gbl.connection.Connect;
import com.di.gbl.model.Empleado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.ResourceBundle;

import static com.di.gbl.connection.Connect.showAlert;

public class InputController implements Initializable {
    @FXML
    TextField name;
    @FXML
    TextField secondName;
    @FXML
    DatePicker birthDate;
    @FXML
    ComboBox category;
    @FXML
    TextField idE;
    @FXML
    Label labelId;


    private static Empleado mod= null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(mod==null){
            labelId.setVisible(false);
            idE.setVisible(false);
        }else{
            idE.setText(String.valueOf(mod.getId()));
            name.setText(mod.getNombre());
            secondName.setText(mod.getApellido());
            birthDate.setValue(LocalDate.parse(mod.getNacimiento().toString()));
            category.getSelectionModel().select(mod.getCategoria());
        }
    }

    public void onButtonSaveClick(ActionEvent actionEvent) throws IOException {
        if(name.getText().isEmpty() || secondName.getText().isEmpty() || birthDate.getValue()==null || category.getSelectionModel().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Error", "Any field is empty");
            return;
        }
        try {
            if(mod==null){
                Connect.insertQuery(name.getText(), secondName.getText(), birthDate.getValue().toString(), category.getValue().toString());}
            else{
                Connect.saveQuery(idE.getText(), name.getText(), secondName.getText(), birthDate.getValue().toString(), category.getValue().toString());}
            vistaPrincipal(actionEvent);
        }catch(RuntimeException e){
            showAlert(Alert.AlertType.ERROR, "Error", "Any field is not right");
        }
        setModifier(null);

    }
    public void onButtonExitClick(ActionEvent actionEvent) throws IOException {
        vistaPrincipal(actionEvent);
        setModifier(null);
    }
    private static void vistaPrincipal(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\resources\\com\\di\\gbl\\empleados.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Empleados");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void setModifier(Empleado empleado){
        mod=empleado;
    }


}
