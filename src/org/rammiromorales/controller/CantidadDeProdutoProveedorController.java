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
import org.rammiromorales.bean.CantidadDeProductoProveedor;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author Donovan Morales
 */
public class CantidadDeProdutoProveedorController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<CantidadDeProductoProveedor> listaDeTipoCantidad;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnListar;

    @FXML
    private TextField txtCodigoCantidadProveedor;

    @FXML
    private TextField txtCantidadDeProductoProveedor;

    @FXML
    private TableView tvlCantidadProveedor;

    @FXML
    private TableColumn colCodigoCantidadProveedor;

    @FXML
    private TableColumn colCantidadDeProductoProveedor;

    @FXML
    private MenuItem btnMenuPrincipal;

    @FXML
    private MenuItem btnClientes;

    @FXML
    private MenuItem btnProgramador;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosTable();
    }

    public void cargarDatosTable() {
        tvlCantidadProveedor.setItems(listaDeTipoCantidadProveedor());
        colCodigoCantidadProveedor.setCellValueFactory(new PropertyValueFactory<CantidadDeProductoProveedor, Integer>("codigoCantidadProveedor"));
        colCantidadDeProductoProveedor.setCellValueFactory(new PropertyValueFactory<CantidadDeProductoProveedor, String>("cantidadProductoProveedor"));
    }

    public ObservableList<CantidadDeProductoProveedor> listaDeTipoCantidadProveedor() {
        ArrayList<CantidadDeProductoProveedor> listador = new ArrayList<>();
        try {
            PreparedStatement consulta = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarCantidadDeProdutoProveedor()}");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                listador.add(new CantidadDeProductoProveedor(resultado.getInt("codigoCantidadProveedor"),
                        resultado.getString("cantidadProductoProveedor")
                ));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return listaDeTipoCantidad = FXCollections.observableList(listador);
    }

    // Metodos de Crud
    public void agregarCantidadProducto() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarTextField();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnActualizar.setDisable(true);
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
                btnActualizar.setDisable(false);
                btnListar.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        CantidadDeProductoProveedor cantidadProducto = new CantidadDeProductoProveedor();
        cantidadProducto.setCantidadProductoProveedor(txtCantidadDeProductoProveedor.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarCantidadDeProdutoProveedor(?)}");
            procedimiento.setString(1, cantidadProducto.getCantidadProductoProveedor());
            procedimiento.execute();
            listaDeTipoCantidad.add(cantidadProducto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCantidadProducto() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarTextField();
                limpiarTextField();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnActualizar.setDisable(false);
                btnListar.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvlCantidadProveedor.getSelectionModel().getSelectedItem() != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro ", "Eliminar Cantidad Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarCantidadDeProdutoProveedor(?)}");
            procedimiento.setInt(1, ((CantidadDeProductoProveedor) tvlCantidadProveedor.getSelectionModel().getSelectedItem()).getCodigoCantidadProveedor());
            procedimiento.execute();
            listaDeTipoCantidad.remove(tvlCantidadProveedor.getSelectionModel().getSelectedItem());
            limpiarTextField();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void editarCantidadProducto() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvlCantidadProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnActualizar.setText(" Actualizar ");
                    btnListar.setText("Cancelar ");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarTextField();
                    txtCodigoCantidadProveedor.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un tipo de producto para editar ");
                }
                break;
            case ACTUALIZAR:
                actualizarProceso();
                btnActualizar.setText(" Editar ");
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarCantidadDeProdutoProveedor(?, ?)}");
            CantidadDeProductoProveedor tipoCantidad = ((CantidadDeProductoProveedor) tvlCantidadProveedor.getSelectionModel().getSelectedItem());
            tipoCantidad.setCantidadProductoProveedor(txtCantidadDeProductoProveedor.getText());
            procedimiento.setInt(1, tipoCantidad.getCodigoCantidadProveedor());
            procedimiento.setString(2, tipoCantidad.getCantidadProductoProveedor());
            procedimiento.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarTextField();
                limpiarTextField();
                btnActualizar.setText("Actualizar");
                btnListar.setText("Cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                btnActualizar.setText("Editar");
                btnListar.setText("Reporte");
                tipoDeOperaciones = operaciones.NINGUNO;

                break;

        }

    }

    // Los tres siguientes metodos abilitan y deshabilitan los text File
    public void desactivarTextField() {
        txtCodigoCantidadProveedor.setEditable(false);
        txtCantidadDeProductoProveedor.setEditable(false);
    }

    public void activarTextField() {
        txtCodigoCantidadProveedor.setEditable(true);
        txtCantidadDeProductoProveedor.setEditable(true);
    }

    public void limpiarTextField() {
        txtCodigoCantidadProveedor.clear();
        txtCantidadDeProductoProveedor.clear();
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
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
