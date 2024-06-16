/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

    private enum operacionesDos {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO

    }
    private operacionesDos tipoDeOperacionesdos = operacionesDos.NINGUNO;

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
    private ObservableList<String> opciones;

    @FXML
    private ListView<String> listViewOpciones;

    @FXML
    private TextField txtSearch;

    @FXML
    private ImageView imgMinimizer;

    @FXML
    private Button btnBuscar;

    public void activarBuscador() {
        txtSearch.setEditable(true);
    }

    public void desactivarBuscador() {
        txtSearch.setEditable(false);
    }

    public void lipiarBuscador() {
        txtSearch.clear();
    }
    private ObservableList<String> lista;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lista = FXCollections.observableArrayList(
                "Tipo Producto", "Compras", "Proveedores", "Clientes",
                "Cargo Empleado", "Empleados", "Email Proveedor", "Telefono Proveedor",
                "Factura", "Productos", "Detalle Compra", "Detalle Factura", "Inventario");
        listViewOpciones.setItems(lista);
    }

    @FXML
    private void buscarDos(KeyEvent event) {
        buscar();
    }

    private void buscar() {
        String searchTerm = txtSearch.getText().toLowerCase();

        ObservableList<String> filteredList = FXCollections.observableArrayList();
        for (String item : lista) {
            if (item.toLowerCase().contains(searchTerm)) {
                filteredList.add(item);
            }
        }

        listViewOpciones.setItems(filteredList);
    }

    public void buttonBuscador() {
        switch (tipoDeOperacionesdos) {
            case NINGUNO:
                activarBuscador();
                listViewOpciones.setVisible(true);
                btnBuscar.setText("CANCELAR");
                btnBuscar.setStyle("    -fx-border-color: black;\n"
                        + "    -fx-background-radius: 10;\n"
                        + "    -fx-border-radius: 10;\n"
                        + "    -fx-background-radius: #FFFFFF;\n"
                        + "    -fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #F28C0F, #FE492C);");

                tipoDeOperacionesdos = operacionesDos.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                listViewOpciones.setVisible(false);
                btnBuscar.setStyle(" ");
                desactivarBuscador();
                lipiarBuscador();
                txtSearch.setText("");
                btnBuscar.setText("BUSCAR");
                tipoDeOperacionesdos = operacionesDos.NINGUNO;
                break;
        }
    }

    @FXML
    private void clickBoton() {
        String selectedItem = listViewOpciones.getSelectionModel().getSelectedItem();
         switch (selectedItem) {
                case "Tipo Producto":
                    tipoDeProducto();
                    break;
                case "Compras":
                    compras();
                    break;
                case "Proveedores":
                    proveedores();
                    break;
                case "Clientes":
                    clientes();
                    break;
                case "Cargo Empleado":
                    cargoEmpleados();
                    break;
                case "Empleados":
                    empleados();
                    break;
                case "Email Proveedor":
                    emailProveedor();
                    break;
                case "Telefono Proveedor":
                    telefonoProveedor();
                    break;
                case "Factura":
                    factura();
                    break;
                case "Productos":
                    producto();
                    break;
                case "Detalle Compra":
                    detalleCompra();
                    break;
                case "Detalle Factura":
                    detalleFactura();
                    break;
                case "Inventario":
                    productoProveedor();
                    break;
        }
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

    public void actionExit(MouseEvent event) {
        javafx.application.Platform.exit();
    }

    public void actionEvent(MouseEvent event) {
        escenarioPrincipal.metodoMinimizar(imgMinimizer);
    }

    public void factura() {
        escenarioPrincipal.ventanaFactura();
    }

    public void producto() {
        escenarioPrincipal.ventanaProducto();
    }

    public void detalleCompra() {
        escenarioPrincipal.ventanaDetalleProducto();
    }

    public void detalleFactura() {
        escenarioPrincipal.ventanaDetalleFactura();
    }

    public void tipoDeProducto() {
        escenarioPrincipal.ventanaTipoProducto();
    }

    public void productoProveedor() {
        escenarioPrincipal.ventanaProductoProveedor();
    }

    public void compras() {
        escenarioPrincipal.ventanaCompras();
    }

    public void clientes() {
        escenarioPrincipal.ventanaMenuClientes();
    }

    public void empleados() {
        escenarioPrincipal.ventanaEmpleados();
    }

    public void cargoEmpleados() {
        escenarioPrincipal.ventanaCargoEmpleado();
    }

    public void telefonoProveedor() {
        escenarioPrincipal.ventanaTelefonoProveedor();
    }

    public void emailProveedor() {
        escenarioPrincipal.ventanaEmailProveedor();
    }

    public void proveedores() {
        escenarioPrincipal.ventanaProveedores();
    }

    public void programador() {
        escenarioPrincipal.ventanaProgramador();
    }
}
