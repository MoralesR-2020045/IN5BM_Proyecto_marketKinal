/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.rammiromorales.system.Principal;

/**
 *
 * @author informatica
 */
public class PrincipalController implements Initializable {

    private enum operaciones {
        MOSTRAR, NINGUNO

    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private Principal escenarioPrincipal;
    @FXML
    private BorderPane separadorPane;
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
    @FXML
    private MenuItem btnCantidadDeProdutoProveedorController;
    @FXML
    private MenuItem btnDetalleCompra;
    @FXML
    private MenuItem btnEmpleados;
    @FXML
    private MenuItem btnEmailProveedores;
    @FXML
    private MenuItem btnTelefonoProveedor;

    @FXML
    private MenuItem btnFactura;

    @FXML
    private MenuItem btnDetalleFactura;

    @FXML
    private ImageView imageLogo;

    @FXML
    private VBox boxPanel;
    @FXML
    private ImageView iconFactura;

    @FXML
    private Button btnFacturaDos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void agregar() throws IOException {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                boxPanel.setPrefHeight(437);
                boxPanel.setPrefWidth(49);
                imageLogo.setVisible(false);
                btnFacturaDos.setPrefWidth(48);
                btnFacturaDos.setPrefHeight(35);
                tipoDeOperaciones = operaciones.MOSTRAR;
                break;
            case MOSTRAR:
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }

    }

    public void facturas() {
        escenarioPrincipal.ventanaFactura();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenuCliente) {
            escenarioPrincipal.ventanaMenuClientes();
        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.ventanaProgramador();
        } else if (event.getSource() == btnCargoEmpleado) {
            escenarioPrincipal.ventanaCargoEmpleado();
        } else if (event.getSource() == btnTipoDeProducto) {
            escenarioPrincipal.ventanaTipoProducto();
        } else if (event.getSource() == btnProveedores) {
            escenarioPrincipal.ventanaProveedores();
        } else if (event.getSource() == btnProducto) {
            escenarioPrincipal.ventanaProducto();
        } else if (event.getSource() == btnCompras) {
            escenarioPrincipal.ventanaCompras();
        } else if (event.getSource() == btnCantidadDeProdutoProveedorController) {
            escenarioPrincipal.cantidadProductoProveedor();
        } else if (event.getSource() == btnDetalleCompra) {
            escenarioPrincipal.ventanaDetalleProducto();
        } else if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.ventanaEmpleados();
        } else if (event.getSource() == btnEmailProveedores) {
            escenarioPrincipal.ventanaEmailProveedor();
        } else if (event.getSource() == btnTelefonoProveedor) {
            escenarioPrincipal.ventanaTelefonoProveedor();
        } else if (event.getSource() == btnDetalleFactura) {
            escenarioPrincipal.ventanaDetalleFactura();
        } else if (event.getSource() == btnFactura) {
            escenarioPrincipal.ventanaFactura();
        }
    }
}
