/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.rammiromorales.system.Principal;

/**
 *
 * @author informatica
 */
public class PrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    
    @FXML 
    private MenuItem btnMenuCliente;
    @FXML 
    private MenuItem btnProgramador;
    @FXML 
    private MenuItem btnCargoEmpleado;
    @FXML
    private MenuItem btnTipoDeProducto;
    @FXML 
    private MenuItem btnProveedores;
    @FXML
    private MenuItem btnProducto;
    @FXML
    private MenuItem btnCompras;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    @FXML 
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuCliente){
            escenarioPrincipal.ventanaMenuClientes();
        }else if (event.getSource() == btnProgramador){
            escenarioPrincipal.ventanaProgramador();
        }else if (event.getSource() == btnCargoEmpleado){
            escenarioPrincipal.ventanaCargoEmpleado();
        }else if (event.getSource() == btnTipoDeProducto){
            escenarioPrincipal.ventanaTipoProducto();
        }else if  (event.getSource() == btnProveedores){
            escenarioPrincipal.ventanaProveedores();
        }else if (event.getSource() == btnProducto){
            escenarioPrincipal.ventanaProducto();
        }else if (event.getSource() == btnCompras){
            escenarioPrincipal.ventanaCompras();
        }
    }
}
