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
    private MenuItem btnDetalleFactura;

    @FXML
    private Button btnFactura;
    @FXML
    private Button btnProductoIcon;
    @FXML
    private Button btnDetalleCompraIcon;
    @FXML
    private Button btnDetalleFacturaIcon;
    @FXML
    private VBox panelInicio;

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
        } else if (event.getSource() == btnCompras) {
            escenarioPrincipal.ventanaCompras();
        } else if (event.getSource() == btnCantidadDeProdutoProveedorController) {
            escenarioPrincipal.cantidadProductoProveedor();
        } else if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.ventanaEmpleados();
        } else if (event.getSource() == btnEmailProveedores) {
            escenarioPrincipal.ventanaEmailProveedor();
        } else if (event.getSource() == btnTelefonoProveedor) {
            escenarioPrincipal.ventanaTelefonoProveedor();
        }
    }

    public void agregar() throws IOException {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                panelInicio.setPrefHeight(430);
                panelInicio.setPrefWidth(190);
                btnFactura.setPrefWidth(205);
                btnFactura.setText("Factura");
                btnProductoIcon.setPrefWidth(205);
                btnProductoIcon.setText("Producto");
                btnDetalleCompraIcon.setPrefWidth(205);
                btnDetalleCompraIcon.setText("Detalle Compra");
                btnDetalleFacturaIcon.setPrefWidth(205);
                btnDetalleFacturaIcon.setText("Detalle Factura");
                tipoDeOperaciones = operaciones.MOSTRAR;
                break;
            case MOSTRAR:
                panelInicio.setPrefHeight(430);
                panelInicio.setPrefWidth(61);
                btnFactura.setPrefWidth(42);
                btnFactura.setText("");
                btnProductoIcon.setPrefWidth(42);
                btnProductoIcon.setText("");
                btnDetalleCompraIcon.setPrefWidth(42);
                btnDetalleCompraIcon.setText("");
                btnDetalleFacturaIcon.setPrefWidth(42);
                btnDetalleFacturaIcon.setText("");
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void factura() {
        escenarioPrincipal.ventanaFactura();
    }

    public void Producto() {
        escenarioPrincipal.ventanaProducto();
    }

    public void DetalleCompra() {
        escenarioPrincipal.ventanaDetalleProducto();
    }

    public void DetalleFactura() {
        escenarioPrincipal.ventanaDetalleFactura();
    }
    public void TipoDeProducto(){
        escenarioPrincipal.ventanaTipoProducto();
    }
    
    public void ProductoProveedor(){
        escenarioPrincipal.ventanaProductoProveedor();
    }
}
