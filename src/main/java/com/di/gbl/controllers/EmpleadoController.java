package com.di.gbl.controllers;


import com.di.gbl.model.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static com.di.gbl.connection.Connect.*;
import static com.di.gbl.controllers.InputController.setModifier;

public class EmpleadoController implements Initializable {
    @FXML
    TableView<Empleado> empleadoView;
    @FXML
    TableColumn<Empleado, Integer> id;
    @FXML
    TableColumn<Empleado, String> name;
    @FXML
    TableColumn<Empleado, String> apellidos;
    @FXML
    TableColumn<Empleado, Date> nacimiento;
    @FXML
    TableColumn<Empleado, String> categoria;
    @FXML
    TextField filterText;
    Empleado seleccionado=null;
    private ObservableList<Empleado> empleados= FXCollections.observableArrayList(consulta());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empleadoView.setItems(empleados);
        filtradoDeValores();
    }
    private void filtradoDeValores() {
        FilteredList<Empleado> filter= new FilteredList<>(empleados, b -> true);

        filterText.textProperty().addListener((observable, oldValue, newValue)->{
            filter.setPredicate(e -> {
                //System.out.println(observable +" "+ oldValue +" "+newValue);
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                if(e.getNombre().indexOf(newValue)!=-1){
                    return true;
                }
                if(e.getApellido().indexOf(newValue)!=-1){
                    return true;
                }
                if(e.getCategoria().indexOf(newValue)!=-1){
                    return true;
                }
                if(Integer.toString(e.getId()).indexOf(newValue)!=-1){
                    return true;
                }
                return false;
            });
        });

        SortedList<Empleado> sortedData = new SortedList<>(filter);
        sortedData.comparatorProperty().bind(empleadoView.comparatorProperty());
        empleadoView.setItems(sortedData);
    }

    public void onButtonInsertClick(ActionEvent actionEvent) throws IOException {
        chargeInputScene(actionEvent);
    }

    public void displaySelected(MouseEvent mouseEvent) {
        seleccionado=empleadoView.getSelectionModel().getSelectedItem();
    }

    public void onButtonDeleteClick(ActionEvent actionEvent) {
        if(seleccionado==null){
            showAlert(Alert.AlertType.INFORMATION, "Atention", "Debe seleccionar un elemento");
        }
        else{
            deleteQuery(seleccionado.getId());
            empleados.remove(seleccionado);
        }

    }

    public void onButtonModifyClick(ActionEvent actionEvent) throws IOException {
        if(seleccionado!=null) {
            setModifier(seleccionado);
            chargeInputScene(actionEvent);
        }else{
            showAlert(Alert.AlertType.INFORMATION, "Atention", "Debe seleccionar un elemento");
        }
    }

    private static void chargeInputScene(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\resources\\com\\di\\gbl\\input.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Empleados");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
