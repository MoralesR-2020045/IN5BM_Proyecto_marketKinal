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
public class ProgramadorController implements Initializable {
    
    @FXML 
    private MenuItem btnPrincipal;
    @FXML 
    private MenuItem btnCliente;
    private Principal escenarioPrincipal;

     @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnPrincipal){
            escenarioPrincipal.ventanaMenuPrincipal();
        } else if (event.getSource() == btnCliente){
            escenarioPrincipal.ventanaMenuClientes();
        }
    }

}
