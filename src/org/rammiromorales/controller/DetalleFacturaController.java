/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.rammiromorales.bean.DetalleFactura;
import org.rammiromorales.bean.Factura;
import org.rammiromorales.bean.Producto;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.system.Principal;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Donovan Morales
 */
public class DetalleFacturaController implements Initializable {

    private ObservableList<DetalleFactura> listaDetalleFactura;
    private ObservableList<Factura> listaDeFactura;
    private ObservableList<Producto> listaProducto;
    private Button controlDeButton;
    private String accion;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO

    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    @FXML
    private ImageView imgMinimizer;
    @FXML
    private AnchorPane ancherPane;
    @FXML
    private Button btnMultiple;
    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnListar;

    @FXML
    private TextField txtCodigoDetalleFactura;

    @FXML
    private TextField txtPrecioUnitario;

    @FXML
    private TextField txtCantidad;

    @FXML
    private MenuItem btnPrincipal;

    @FXML
    private MenuItem btnClientes;

    @FXML
    private MenuItem btnProgramador;

    @FXML
    private TableView tvlDetalleFactura;

    @FXML
    private TableColumn colCodigoDetalleFactura;

    @FXML
    private TableColumn colPrecioUnitario;

    @FXML
    private TableColumn colCantidad;

    @FXML
    private TableColumn colNumeroFactura;

    @FXML
    private TableColumn colCodigoProducto;

    @FXML
    private ComboBox cmbCodigoProducto;

    @FXML
    private ComboBox cmbNumeroFactura;

    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnBuscar;

    public void activarBuscador() {
        txtBuscar.setEditable(true);
    }

    public void desactivarBuscador() {
        txtBuscar.setEditable(false);
    }

    public void lipiarBuscador() {
        txtBuscar.clear();
        tvlDetalleFactura.setItems(listaDetalleFactura);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaDatos();
        cmbCodigoProducto.setItems(getProducto());
        cmbNumeroFactura.setItems(getFactura());
    }

    public void cargaDatos() {
        tvlDetalleFactura.setItems(getDetalleFactura());
        colCodigoDetalleFactura.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("codigoDetalleFactura"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("precioUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("cantidad"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("numeroFactura"));
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("codigoProducto"));
    }

    public ObservableList<DetalleFactura> getDetalleFactura() {
        ArrayList<DetalleFactura> lista = new ArrayList<DetalleFactura>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("call sp_listarDetallesFacturas()");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleFactura(resultado.getInt("codigoDetalleFactura"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroFactura"),
                        resultado.getString("codigoProducto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetalleFactura = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Factura> getFactura() {
        ArrayList<Factura> lista = new ArrayList<Factura>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getDate("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDeFactura = FXCollections.observableList(lista);
    }

    public ObservableList<Producto> getProducto() {
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("call sp_listarProductos()");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Producto(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getInt("existencia"),
                        resultado.getInt("idProductoProveedor"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProducto = FXCollections.observableArrayList(lista);
    }

    public void selecionarElementos() {
        txtCodigoDetalleFactura.setText(String.valueOf(((DetalleFactura) tvlDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura()));
        txtCantidad.setText(String.valueOf(((DetalleFactura) tvlDetalleFactura.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbNumeroFactura.getSelectionModel().select(buscarFactura(((DetalleFactura) tvlDetalleFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        cmbCodigoProducto.getSelectionModel().select(buscarProducto(((DetalleFactura) tvlDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoProducto()));

    }

    public Factura buscarFactura(int codigoTipoProducto) {
        Factura resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarFactura(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Factura(registro.getInt("numeroFactura"),
                        registro.getString("estado"),
                        registro.getDouble("totalFactura"),
                        registro.getDate("fechaFactura"),
                        registro.getInt("codigoCliente"),
                        registro.getInt("codigoEmpleado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Producto buscarProducto(String codigoTipoProducto) {
        Producto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarProductos(?)}");
            procedimiento.setString(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Producto(registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getInt("existencia"),
                        registro.getInt("idProductoProveedor"),
                        registro.getInt("codigoTipoProducto"),
                        registro.getInt("codigoProveedor")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void buttonBuscador() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarBuscador();
                btnBuscar.setText("CANCELAR");
                btnBuscar.setStyle("    -fx-border-color: black;\n"
                        + "    -fx-background-radius: 10;\n"
                        + "    -fx-border-radius: 10;\n"
                        + "    -fx-background-radius: #FFFFFF;\n"
                        + "    -fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #F28C0F, #FE492C);");

                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                btnBuscar.setStyle(" ");
                desactivarBuscador();
                lipiarBuscador();
                txtBuscar.setText("");
                btnBuscar.setText("BUSCAR");
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void buscar() {
        String criterio = txtBuscar.getText().trim();
        if (!criterio.isEmpty()) {
            filtrarDatos(criterio);
        } else {
            cargaDatos(); // Si no hay criterio, se cargan todos los datos
        }
    }

    // Método para filtrar los datos de la tabla
    public void filtrarDatos(String criterio) {
        ObservableList<DetalleFactura> detallesFiltrados = FXCollections.observableArrayList();
        for (DetalleFactura detalle : listaDetalleFactura) {
            if (String.valueOf(detalle.getCodigoDetalleFactura()).contains(criterio)
                    || String.valueOf(detalle.getPrecioUnitario()).contains(criterio)
                    || String.valueOf(detalle.getCantidad()).contains(criterio)
                    || String.valueOf(detalle.getNumeroFactura()).contains(criterio)
                    || detalle.getCodigoProducto().contains(criterio)) {
                detallesFiltrados.add(detalle);
            }
        }
        tvlDetalleFactura.setItems(detallesFiltrados);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnMultiple.setStyle("    -fx-border-color: black;\n"
                        + "    -fx-background-radius: 10;\n"
                        + "    -fx-border-radius: 10;\n"
                        + "    -fx-background-radius: #FFFFFF;\n"
                        + "    -fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #F28C0F, #FE492C);");
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                btnMultiple.setStyle("");
                guardar();
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargaDatos();
                break;
        }
    }

    public void guardar() {
        double cero = 0.0;
        DetalleFactura registro = new DetalleFactura();
        registro.setCodigoDetalleFactura(Integer.parseInt(txtCodigoDetalleFactura.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setNumeroFactura(((Factura) cmbNumeroFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
        registro.setCodigoProducto(((Producto) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarDetalleFactura(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, cero);
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
            procedimiento.execute();

            listaDetalleFactura.add(registro);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                btnAgregar.setText("Agregar");
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvlDetalleFactura.getSelectionModel().getSelectedItem() != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro ", "Eliminar Detalle Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confirmacion == JOptionPane.YES_NO_OPTION) {
                        eliminarProceso();
                    } else {
                        JOptionPane.showMessageDialog(null, "Se ha cancelado la eliminacion de la fila ");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar una fila para hacer una eliminacion");
                }
                break;
        }
    }

    public void eliminarProceso() {
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarDetalleFactura(?)}");
            procedimiento.setInt(1, ((DetalleFactura) tvlDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura());
            procedimiento.execute();
            listaDetalleFactura.remove(tvlDetalleFactura.getSelectionModel().getSelectedItem());
            desactivarControles();
            limpiarControles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvlDetalleFactura.getSelectionModel().getSelectedItem() != null) {
                    btnMultiple.setStyle("    -fx-border-color: black;\n"
                            + "    -fx-background-radius: 10;\n"
                            + "    -fx-border-radius: 10;\n"
                            + "    -fx-background-radius: #FFFFFF;\n"
                            + "    -fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #F28C0F, #FE492C);");
                    activarControles();
                    txtCodigoDetalleFactura.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un proveedor para editar ");
                }
                break;
            case ACTUALIZAR:
                btnMultiple.setStyle("");
                actualizarProceso();
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargaDatos();
                break;
        }
    }

    public void actualizarProceso() {
        try {
            double cero = 0.0;
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarDetalleFactura(?, ?, ?, ?, ?)}");
            DetalleFactura registro = ((DetalleFactura) tvlDetalleFactura.getSelectionModel().getSelectedItem());

            registro.setCodigoDetalleFactura(Integer.parseInt(txtCodigoDetalleFactura.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setNumeroFactura(((Factura) cmbNumeroFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
            registro.setCodigoProducto(((Producto) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());

            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, cero);
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
            procedimiento.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Actualizar");
                btnListar.setText("Cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnListar.setText("Reporte");
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtCodigoDetalleFactura.setEditable(false);
        txtCantidad.setEditable(false);
        cmbCodigoProducto.setDisable(true);
        cmbNumeroFactura.setDisable(true);
    }

    public void activarControles() {
        txtCodigoDetalleFactura.setEditable(true);
        txtCantidad.setEditable(true);
        cmbCodigoProducto.setDisable(false);
        cmbNumeroFactura.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoDetalleFactura.clear();
        txtCantidad.clear();
        cmbCodigoProducto.getSelectionModel().clearSelection();
        cmbNumeroFactura.getSelectionModel().clearSelection();
    }

    public void actionExit(MouseEvent event) {
        javafx.application.Platform.exit();
    }

    public void actionEvent(MouseEvent event) {
        escenarioPrincipal.metodoMinimizar(imgMinimizer);
    }

    public void agregados() {
        visibilidadDePanel(btnAgregar);
    }

    public void eliminados() {
        visibilidadDePanel(btnEliminar);
    }

    public void editados() {
        visibilidadDePanel(btnEditar);
    }

    public void visibilidadDePanel(Button button) {
        if (controlDeButton == button) {
            tvlDetalleFactura.setPrefHeight(406);
            tvlDetalleFactura.setLayoutY(147);
            ancherPane.setVisible(true);
            controlDeButton = null;
        } else {
            tvlDetalleFactura.setPrefHeight(240);
            tvlDetalleFactura.setLayoutY(313);
            ancherPane.setVisible(false);
            if (button == btnAgregar) {
                btnMultiple.setText("GUARDAR");
                accion = "Agregar";
            } else if (button == btnEliminar) {
                btnMultiple.setText("ELIMINAR");
                accion = "Eliminar";
            } else if (button == btnEditar) {
                btnMultiple.setText("EDITAR");
                accion = "Actualizar";
            }
            controlDeButton = button;
        }
    }

    public void multipleAcciones() {
        switch (accion) {
            case "Agregar":
                agregar();
                break;
            case "Eliminar":
                eliminar();
                break;
            case "Actualizar":
                editar();
                break;
        }
    }

    public void cancelar() {
        switch (accion) {
            case "Agregar":
                cancelarAgregar();
                break;
            case "Actualizar":
                cancelarEditar();
                break;
        }
    }

    public void cancelarAgregar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                int Confirma = JOptionPane.showConfirmDialog(null, "Desea Cancelar el proceso de Agregar un Detalle de Factura", " Cancerlar ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (Confirma == JOptionPane.YES_NO_OPTION) {
                    limpiarControles();
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha cancelado puedes seguir Agregando");
                    tipoDeOperaciones = operaciones.NINGUNO;
                }
                break;
        }
    }

    public void cancelarEditar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                int Confirma = JOptionPane.showConfirmDialog(null, "Desea Cancelar el proceso de Editar un Detalle de Factura", " Cancerlar ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (Confirma == JOptionPane.YES_NO_OPTION) {
                    limpiarControles();
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha cancelado puedes seguir Editando");
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }
                break;
        }
    }

    public void inicio() {
        escenarioPrincipal.ventanaMenuPrincipal();
    }

    public void producto() {
        escenarioPrincipal.ventanaTipoProducto();
    }

    public void factura() {
        escenarioPrincipal.ventanaProveedores();
    }

    public void principal() {
        escenarioPrincipal.ventanaMenuPrincipal();
    }
    
    public void login(){
        escenarioPrincipal.ventanaLoginPrincipal();
    }
}
