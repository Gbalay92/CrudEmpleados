package com.di.gbl.connection;

import com.di.gbl.model.Empleado;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.locks.StampedLock;

public class Connect {
    private final static String URL="jdbc:mysql://localhost/";
    private static Connection conexion;

    public static void conexionSql(String user, String database, String password) throws SQLException {
        conexion= DriverManager.getConnection(URL, user, password);
        Statement stm= conexion.createStatement();
        String creacion="CREATE DATABASE IF NOT EXISTS "+database;
        stm.execute(creacion);
        conexion.close();
        conexion= DriverManager.getConnection(URL + database, user, password);



    }
    public static ArrayList<Empleado> consulta(){
        ArrayList<Empleado>empleados=new ArrayList<Empleado>();
        Statement stmt = null;
        try {
            createDatabase();
            stmt = conexion.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM empleado");
            while (rs.next()) {
                Empleado emp = new Empleado(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(5),rs.getDate(4));
                empleados.add(emp);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleados;
    }

    private static void createDatabase() throws SQLException {
        Statement stmt = conexion.createStatement();
       {
            String sql = "CREATE TABLE IF NOT EXISTS empleado " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " nombre VARCHAR(255), " +
                    " apellidos VARCHAR(255), " +
                    " fecha_nacimiento DATE, " +
                    " categoria ENUM('A1','A2','B1','B2','C1','C2')," +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);
            stmt.close();
    }
    }
    public static void deleteQuery(int id){
        Statement stmt = null;
        String query = "DELETE FROM empleado " +
                "WHERE id = "+id+"";
        try {
            stmt = conexion.createStatement();

            stmt.executeUpdate(query);
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertQuery(String name, String apellidos, String nacimiento, String categoria){
        Statement stmt = null;
        String query="INSERT INTO empleado(nombre, apellidos, fecha_nacimiento, categoria)" +
                "VALUES('"+name+"','"+apellidos+"','"+nacimiento+"','"+categoria+"')";
        try {
            stmt = conexion.createStatement();
            stmt.executeUpdate(query);
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public static void saveQuery(String id, String name, String seconName, String birthDate, String category) {
        Statement stmt = null;
        String query ="UPDATE empleado " +
                "SET nombre = '"+ name +
                "', apellidos = '" +seconName+
                "', fecha_nacimiento = '"+birthDate+
                "', categoria = '" + category+
                "' WHERE id = '"+id+"'";
        try {
            stmt = conexion.createStatement();

            int filas= stmt.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

