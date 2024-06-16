/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.rammiromorales.bean.Usuario;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author Donovan Morales
 */
public class UsuarioController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        GUARDAR, NINGUNO,
    }
    private operaciones tipoOperacion = operaciones.NINGUNO;

    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtApellidoUsuario;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        desactivarControles();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void ventanaLogin() {
        escenarioPrincipal.ventanaLoginPrincipal();
    }

    public void nuevo() {
        switch (tipoOperacion) {
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("GUARDAR");
                btnEliminar.setText("CANCELAR");
                tipoOperacion = operaciones.GUARDAR;
                break;

            case GUARDAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("AGREGAR");
                btnEliminar.setText("ELIMINAR");
                tipoOperacion = operaciones.NINGUNO;
                ventanaLogin();
                break;
        }
    }

    public void guardar() {
        Usuario registro = new Usuario();
        registro.setNombreUsuario(txtNombreUsuario.getText());
        registro.setApellidosUsuario(txtApellidoUsuario.getText());
        registro.setUsuarioLogin(txtUsuario.getText());
        registro.setContrasena(txtPassword.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{Call sp_AgregarUsuario(?,?,?,?)}");
            procedimiento.setString(1, registro.getNombreUsuario());
            procedimiento.setString(2, registro.getApellidosUsuario());
            procedimiento.setString(3, registro.getUsuarioLogin());
            procedimiento.setString(4, registro.getContrasena());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoOperacion) {
            case GUARDAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("     Nuevo");
                btnEliminar.setText("    Eliminar");
                tipoOperacion = operaciones.NINGUNO;
                break;
        }
    }

    public void desactivarControles() {
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuario.setEditable(false);
        txtPassword.setEditable(false);
    }

    public void activarControles() {
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuario.setEditable(true);
        txtPassword.setEditable(true);
    }

    public void limpiarControles() {
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuario.clear();
        txtPassword.clear();
    }
}
