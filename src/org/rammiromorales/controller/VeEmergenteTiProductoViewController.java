/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.rammiromorales.bean.TipoProducto;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author Donovan Morales
 */
public class VeEmergenteTiProductoViewController implements Initializable {
    private TipoProductoViewController tipoProductoController;
    private Stage stage;
    private Principal escenarioPrincipal;
    @FXML
    private TextField txtID;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public TipoProducto guardar() {
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setDescripcion(txtDescripcion.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarTipoProducto(?)}");
            procedimiento.setString(1, tipoProducto.getDescripcion());
            procedimiento.execute();
            tipoProductoController.listaDeTipoProducto().add(tipoProducto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoProducto;
    }

    
    
}
