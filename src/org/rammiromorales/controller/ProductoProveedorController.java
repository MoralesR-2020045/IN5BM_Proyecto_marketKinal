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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.rammiromorales.bean.ProductoProveedor;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class ProductoProveedorController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<ProductoProveedor> listadoProductoProveedor;

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
    private TextField txtIdPProveedor;

    @FXML
    private TextField txtNombrePProveedor;

    @FXML
    private TextField txtDescripcionPProveedor;

    @FXML
    private TextField txtPrecioPProveedor;

    @FXML
    private MenuItem btnPrincipal;

    @FXML
    private MenuItem btnClientes;

    @FXML
    private MenuItem btnProgramador;

    @FXML
    private TableView tvlProductoProveedor;

    @FXML
    private TableColumn colIdPProveedor;

    @FXML
    private TableColumn colNombrePProveedor;

    @FXML
    private TableColumn colDescripcionPProveedor;

    @FXML
    private TableColumn colPrecioPProveedor;

    @FXML
    private TableColumn colCantidadPProveedor;

    @FXML
    private TableColumn colExistenciaDescripcion;

    @FXML
    private TableColumn colExistenciaTotalProducto;

    @FXML
    private TextField txtExistenciaDescripcion;

    @FXML
    private TextField txtExistenciaTotalProducto;

    @FXML
    private TextField txtCantidadPProveedor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosTable();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void cargarDatosTable() {
        tvlProductoProveedor.setItems(listaProductoProveedor());
        colIdPProveedor.setCellValueFactory(new PropertyValueFactory<ProductoProveedor, Integer>("idProductoProveedor"));
        colNombrePProveedor.setCellValueFactory(new PropertyValueFactory<ProductoProveedor, String>("nombreProductoProveedor"));
        colDescripcionPProveedor.setCellValueFactory(new PropertyValueFactory<ProductoProveedor, String>("descripcionProducto"));
        colPrecioPProveedor.setCellValueFactory(new PropertyValueFactory<ProductoProveedor, Double>("precioProveedor"));
        colCantidadPProveedor.setCellValueFactory(new PropertyValueFactory<ProductoProveedor, Integer>("cantidadDeProducto"));
        colExistenciaDescripcion.setCellValueFactory(new PropertyValueFactory<ProductoProveedor, Integer>("existenciaPorDescripcion"));
        colExistenciaTotalProducto.setCellValueFactory(new PropertyValueFactory<ProductoProveedor, Integer>("existenciaTotalDelProducto"));
    }

    public void seleccionarDatos() {
        txtIdPProveedor.setText(String.valueOf(((ProductoProveedor) tvlProductoProveedor.getSelectionModel().getSelectedItem()).getIdProductoProveedor()));
        txtNombrePProveedor.setText(((ProductoProveedor) tvlProductoProveedor.getSelectionModel().getSelectedItem()).getNombreProductoProveedor());
        txtDescripcionPProveedor.setText(((ProductoProveedor) tvlProductoProveedor.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioPProveedor.setText(String.valueOf(((ProductoProveedor) tvlProductoProveedor.getSelectionModel().getSelectedItem()).getPrecioProveedor()));
        txtCantidadPProveedor.setText(String.valueOf(((ProductoProveedor) tvlProductoProveedor.getSelectionModel().getSelectedItem()).getCantidadDeProducto()));
        txtExistenciaDescripcion.setText(String.valueOf(((ProductoProveedor) tvlProductoProveedor.getSelectionModel().getSelectedItem()).getExistenciaPorDescripcion()));
        txtExistenciaTotalProducto.setText(String.valueOf(((ProductoProveedor) tvlProductoProveedor.getSelectionModel().getSelectedItem()).getExistenciaTotalDelProducto()));

    }

    public ObservableList<ProductoProveedor> listaProductoProveedor() {
        ArrayList<ProductoProveedor> listado = new ArrayList<>();
        try {
            PreparedStatement consulta = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarProductoProveedor()}");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                listado.add(new ProductoProveedor(resultado.getInt("idProductoProveedor"),
                        resultado.getString("nombreProductoProveedor"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioProveedor"),
                        resultado.getInt("cantidadDeProducto"),
                        resultado.getInt("existenciaPorDescripcion"),
                        resultado.getInt("existenciaTotalDelProducto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listadoProductoProveedor = FXCollections.observableList(listado);
    }

    public void agregarProductoProveedor() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarTextField();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnListar.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                cargarDatosTable();
                desactivarTextField();
                limpiarTextField();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnListar.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        ProductoProveedor productoProveedor = new ProductoProveedor();
        productoProveedor.setNombreProductoProveedor(txtNombrePProveedor.getText());
        productoProveedor.setDescripcionProducto(txtDescripcionPProveedor.getText());
        productoProveedor.setPrecioProveedor(Double.parseDouble(txtPrecioPProveedor.getText()));
        productoProveedor.setCantidadDeProducto(Integer.parseInt(txtCantidadPProveedor.getText()));
        productoProveedor.setExistenciaPorDescripcion(Integer.parseInt(txtExistenciaDescripcion.getText()));
        productoProveedor.setExistenciaTotalDelProducto(Integer.parseInt(txtExistenciaTotalProducto.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarProductoProveedor(?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, productoProveedor.getNombreProductoProveedor());
            procedimiento.setString(2, productoProveedor.getDescripcionProducto());
            procedimiento.setDouble(3, productoProveedor.getPrecioProveedor());
            procedimiento.setInt(4, productoProveedor.getCantidadDeProducto());
            procedimiento.setInt(5, productoProveedor.getExistenciaPorDescripcion());
            procedimiento.setInt(6, productoProveedor.getExistenciaTotalDelProducto());
            procedimiento.execute();
            listadoProductoProveedor.add(productoProveedor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCargoEmpleado() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarTextField();
                limpiarTextField();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnListar.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvlProductoProveedor.getSelectionModel().getSelectedItem() != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro ", "Eliminar Producto Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarProductoProveedor(?)}");
            procedimiento.setInt(1, ((ProductoProveedor) tvlProductoProveedor.getSelectionModel().getSelectedItem()).getIdProductoProveedor());
            procedimiento.execute();
            listadoProductoProveedor.remove(tvlProductoProveedor.getSelectionModel().getSelectedItem());
            limpiarTextField();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvlProductoProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText(" Actualizar ");
                    btnListar.setText("Cancelar ");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarTextField();
                    txtIdPProveedor.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar ");
                }
                break;
            case ACTUALIZAR:
                actualizarProceso();
                btnEditar.setText(" Editar ");
                btnListar.setText("Reporte ");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarTextField();
                limpiarTextField();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatosTable();
                break;
        }
    }

    public void actualizarProceso() {
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarProductoProveedor(?, ?, ?, ?, ?, ?, ?)}");
            ProductoProveedor registro = ((ProductoProveedor) tvlProductoProveedor.getSelectionModel().getSelectedItem());
            registro.setIdProductoProveedor(Integer.parseInt(txtIdPProveedor.getText()));
            registro.setNombreProductoProveedor(txtNombrePProveedor.getText());
            registro.setDescripcionProducto(txtDescripcionPProveedor.getText());
            registro.setPrecioProveedor(Double.parseDouble(txtPrecioPProveedor.getText()));
            registro.setCantidadDeProducto(Integer.parseInt(txtCantidadPProveedor.getText()));
            registro.setExistenciaPorDescripcion(Integer.parseInt(txtExistenciaDescripcion.getText()));
            registro.setExistenciaTotalDelProducto(Integer.parseInt(txtExistenciaTotalProducto.getText()));
            procedimiento.setInt(1, registro.getIdProductoProveedor());
            procedimiento.setString(2, registro.getNombreProductoProveedor());
            procedimiento.setString(3, registro.getDescripcionProducto());
            procedimiento.setDouble(4, registro.getPrecioProveedor());
            procedimiento.setInt(5, registro.getCantidadDeProducto());
            procedimiento.setInt(6, registro.getExistenciaPorDescripcion());
            procedimiento.setInt(7, registro.getExistenciaTotalDelProducto());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarTextField();
                limpiarTextField();
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

    public void desactivarTextField() {
        txtIdPProveedor.setEditable(false);
        txtNombrePProveedor.setEditable(false);
        txtDescripcionPProveedor.setEditable(false);
        txtPrecioPProveedor.setEditable(false);
        txtCantidadPProveedor.setEditable(false);
        txtExistenciaDescripcion.setEditable(false);
        txtExistenciaTotalProducto.setEditable(false);
    }

    public void activarTextField() {
        txtIdPProveedor.setEditable(true);
        txtNombrePProveedor.setEditable(true);
        txtDescripcionPProveedor.setEditable(true);
        txtPrecioPProveedor.setEditable(true);
        txtCantidadPProveedor.setEditable(true);
        txtExistenciaDescripcion.setEditable(true);
        txtExistenciaTotalProducto.setEditable(true);
    }

    public void limpiarTextField() {
        txtIdPProveedor.clear();
        txtNombrePProveedor.clear();
        txtDescripcionPProveedor.clear();
        txtPrecioPProveedor.clear();
        txtCantidadPProveedor.clear();
        txtExistenciaDescripcion.clear();
        txtExistenciaTotalProducto.clear();
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnPrincipal) {
            escenarioPrincipal.ventanaMenuPrincipal();
        } else if (event.getSource() == btnClientes) {
            escenarioPrincipal.ventanaMenuClientes();
        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.ventanaProgramador();
        }
    }
}
