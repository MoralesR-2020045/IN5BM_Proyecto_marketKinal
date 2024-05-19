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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.rammiromorales.bean.Compras;
import org.rammiromorales.bean.DetalleCompra;
import org.rammiromorales.bean.Producto;
import org.rammiromorales.bean.Proveedores;
import org.rammiromorales.bean.TipoProducto;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author Donovan Morales
 */
public class DetalleCompraViewController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<Producto> listaProducto;
    private ObservableList<DetalleCompra> listaDetalleCompra;
    private ObservableList<Compras> listadoDeCompras;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO

    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnListar;

    @FXML
    private MenuItem btnMenuPrincipal;

    @FXML
    private MenuItem btnClientes;

    @FXML
    private MenuItem btnProgramador;

    @FXML
    private TextField txtCodigoDetalleCompra;

    @FXML
    private TextField txtCostoUnitario;

    @FXML
    private TextField txtCantidad;

    @FXML
    private ComboBox cmbCodigoProducto;

    @FXML
    private ComboBox cmbNumeroDocumento;

    @FXML
    private TableView tblDetalleProducto;

    @FXML
    private TableColumn colCodigoDetalleCompra;

    @FXML
    private TableColumn colCostoUnitario;

    @FXML
    private TableColumn colCantidad;

    @FXML
    private TableColumn colCodigoProducto;

    @FXML
    private TableColumn colNumeroDocumento;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaDatos();
        cmbCodigoProducto.setItems(getProducto());
        cmbNumeroDocumento.setItems(listaDeCompras());
    }

    public void cargaDatos() {
        tblDetalleProducto.setItems(getDetalleCompra());
        colCodigoDetalleCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("codigoProducto"));
        colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));
    }

    public void seleccionarELemento() {
        txtCodigoDetalleCompra.setText(String.valueOf(((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
        txtCostoUnitario.setText(String.valueOf(((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCantidad.setText(String.valueOf(((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbCodigoProducto.getSelectionModel().select(buscarProductos(String.valueOf(((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getCodigoProducto())));
        cmbNumeroDocumento.getSelectionModel().select(buscarCompra(((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
    }

    public Producto buscarProductos(String codigoProducto) {
        Producto resultado = null;
        try {
            PreparedStatement p = Conexion.getInstancia().getConexion().prepareCall("call sp_buscarProductos(?);");
            p.setString(1, codigoProducto);
            ResultSet registro = p.executeQuery();
            while (registro.next()) {
                resultado = new Producto(registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoTipoProducto"),
                        registro.getInt("codigoProveedor")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Compras buscarCompra(int compra) {
        Compras resultado = null;
        try {
            PreparedStatement p = Conexion.getInstancia().getConexion().prepareCall("call sp_buscarCompras(?);");
            p.setInt(1, compra);
            ResultSet registro = p.executeQuery();
            while (registro.next()) {
                resultado = new Compras(registro.getInt("numeroDocumento"),
                        registro.getDate("fechaDocumento"),
                        registro.getString("descripcion"),
                        registro.getDouble("totalDocumento"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("call sp_listarDetalleCompra()");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(
                        resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("codigoProducto"),
                        resultado.getInt("numeroDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetalleCompra = FXCollections.observableArrayList(lista);
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
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProducto = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Compras> listaDeCompras() {
        ArrayList<Compras> listado = new ArrayList<>();
        try {
            PreparedStatement consulta = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                listado.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getDate("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")
                ));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return listadoDeCompras = FXCollections.observableList(listado);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnListar.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnListar.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargaDatos();
                break;
        }

    }

    public void guardar() {
        String png = "png";
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCodigoDetalleCompra.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setCodigoProducto(((Producto) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroDocumento(((Compras) cmbNumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarDetalleCompra(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();

            listaDetalleCompra.add(registro);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void eliminarProveedores() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                activarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnListar.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblDetalleProducto.getSelectionModel().getSelectedItem() != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro ", "Eliminar Detalle Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confirmacion == JOptionPane.YES_NO_OPTION) {
                        eliminarProceso();
                        cargaDatos();
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarDetalleCompra(?)}");
            procedimiento.setInt(1, ((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
            procedimiento.execute();
            listadoDeCompras.remove(tblDetalleProducto.getSelectionModel().getSelectedItem());
            limpiarControles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblDetalleProducto.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText(" Actualizar ");
                    btnListar.setText("Cancelar ");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtCodigoDetalleCompra.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un proveedor para editar ");
                }
                break;
            case ACTUALIZAR:
                actualizarProceso();
                btnEditar.setText(" Editar ");
                btnListar.setText("Reporte ");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargaDatos();
                break;
        }
    }

    public void actualizarProceso() {
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarDetalleCompra(?, ?, ?, ?, ?)}");
            DetalleCompra registro = ((DetalleCompra) tblDetalleProducto.getSelectionModel().getSelectedItem());
            registro.setCodigoDetalleCompra(Integer.parseInt(txtCodigoDetalleCompra.getText()));
            registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setCodigoProducto(((Producto) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setNumeroDocumento(((Compras) cmbNumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());

            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void activarControles() {
        txtCodigoDetalleCompra.setEditable(true);
        txtCostoUnitario.setEditable(true);
        txtCantidad.setEditable(true);
        cmbCodigoProducto.setDisable(false);
        cmbNumeroDocumento.setDisable(false);
    }

    public void desactivarControles() {
        txtCodigoDetalleCompra.setEditable(false);
        txtCostoUnitario.setEditable(false);
        txtCantidad.setEditable(false);
        cmbCodigoProducto.setDisable(true);
        cmbNumeroDocumento.setDisable(true);
    }

    public void limpiarControles() {
        txtCodigoDetalleCompra.clear();
        txtCostoUnitario.clear();
        txtCantidad.clear();
        cmbCodigoProducto.getSelectionModel().getSelectedItem();
        cmbNumeroDocumento.getSelectionModel().getSelectedItem();
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenuPrincipal) {
            escenarioPrincipal.ventanaMenuPrincipal();
        } else if (event.getSource() == btnClientes) {
            escenarioPrincipal.ventanaMenuClientes();
        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.ventanaProgramador();
        }

    }
}
