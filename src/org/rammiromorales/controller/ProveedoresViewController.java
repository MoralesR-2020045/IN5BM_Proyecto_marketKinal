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
import java.util.HashMap;
import java.util.Map;
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
import org.rammiromorales.bean.CargoEmpleado;
import org.rammiromorales.bean.Proveedores;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.report.GenerarReportes;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author Donovan Morales
 */
public class ProveedoresViewController implements Initializable {

    private Principal escenarioPrincipal;

    private ObservableList<Proveedores> listaDeProveedores;

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
    private TableView tvlProveedores;

    @FXML
    private TableColumn colCodigoProveedor;

    @FXML
    private TableColumn colNITProveedor;

    @FXML
    private TableColumn colNombresProveedor;

    @FXML
    private TableColumn colApellidosProveedor;

    @FXML
    private TableColumn colDireccionProveedor;

    @FXML
    private TableColumn colRazonSocial;

    @FXML
    private TableColumn colContactoPrincipal;

    @FXML
    private TableColumn colPaginaWeb;

    @FXML
    private TextField txtNITProveedor;

    @FXML
    private TextField txtNombresProveedor;

    @FXML
    private TextField txtApellidosProveedor;

    @FXML
    private TextField txtCodigoProveedor;

    @FXML
    private TextField txtRazonSocial;

    @FXML
    private TextField txtContactoPrincipal;

    @FXML
    private TextField txtPaginaWeb;

    @FXML
    private TextField txtDireccionProveedor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosTable();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargarDatosTable() {
        tvlProveedores.setItems(listaDeProveedores());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNITProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITProveedor"));
        colNombresProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombresProveedor"));
        colApellidosProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoPrincipal.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }

    public ObservableList<Proveedores> listaDeProveedores() {
        ArrayList<Proveedores> listado = new ArrayList<>();
        try {
            PreparedStatement consulta = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarProveedores()}");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                listado.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return listaDeProveedores = FXCollections.observableList(listado);
    }

    // Metodos de Crud
    public void agregarProveedores() {
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
        Proveedores proveedores = new Proveedores();
        proveedores.setNITProveedor(txtNITProveedor.getText());
        proveedores.setNombresProveedor(txtNombresProveedor.getText());
        proveedores.setApellidosProveedor(txtApellidosProveedor.getText());
        proveedores.setDireccionProveedor(txtDireccionProveedor.getText());
        proveedores.setRazonSocial(txtRazonSocial.getText());
        proveedores.setContactoPrincipal(txtContactoPrincipal.getText());
        proveedores.setPaginaWeb(txtPaginaWeb.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarProveedores(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, proveedores.getNITProveedor());
            procedimiento.setString(2, proveedores.getNombresProveedor());
            procedimiento.setString(3, proveedores.getApellidosProveedor());
            procedimiento.setString(4, proveedores.getDireccionProveedor());
            procedimiento.setString(5, proveedores.getRazonSocial());
            procedimiento.setString(6, proveedores.getContactoPrincipal());
            procedimiento.setString(7, proveedores.getPaginaWeb());
            procedimiento.execute();
            listaDeProveedores.add(proveedores);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarProveedores() {
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
                if (tvlProveedores.getSelectionModel().getSelectedItem() != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro ", "Eliminar al proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarProveedores(?)}");
            procedimiento.setInt(1, ((Proveedores) tvlProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.execute();
            listaDeProveedores.remove(tvlProveedores.getSelectionModel().getSelectedItem());
            limpiarTextField();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvlProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText(" Actualizar ");
                    btnListar.setText("Cancelar ");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarTextField();
                    txtCodigoProveedor.setEditable(false);
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
                desactivarTextField();
                limpiarTextField();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatosTable();
                break;
        }
    }

    public void actualizarProceso() {
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarProveedores(?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores proveedores = ((Proveedores) tvlProveedores.getSelectionModel().getSelectedItem());
            proveedores.setNITProveedor(txtNITProveedor.getText());
            proveedores.setNombresProveedor(txtNombresProveedor.getText());
            proveedores.setApellidosProveedor(txtApellidosProveedor.getText());
            proveedores.setDireccionProveedor(txtDireccionProveedor.getText());
            proveedores.setRazonSocial(txtRazonSocial.getText());
            proveedores.setContactoPrincipal(txtContactoPrincipal.getText());
            proveedores.setPaginaWeb(txtPaginaWeb.getText());
            procedimiento.setInt(1, proveedores.getCodigoProveedor());
            procedimiento.setString(2, proveedores.getNITProveedor());
            procedimiento.setString(3, proveedores.getNombresProveedor());
            procedimiento.setString(4, proveedores.getApellidosProveedor());
            procedimiento.setString(5, proveedores.getDireccionProveedor());
            procedimiento.setString(6, proveedores.getRazonSocial());
            procedimiento.setString(7, proveedores.getContactoPrincipal());
            procedimiento.setString(8, proveedores.getPaginaWeb());
            procedimiento.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                imprimirReporte();
                break;

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


    

        public void imprimirReporte() {
        Map parametros = new HashMap();
        parametros.put("codigoProveedor", null);
        GenerarReportes.mostrarReportes("ReporteProveedores.jasper.", "Reporte Proveedor", parametros);
    }

    
    // Los tres siguientes metodos abilitan y deshabilitan los text File
    

    public void desactivarTextField() {
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(false);
        txtNombresProveedor.setEditable(false);
        txtApellidosProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtContactoPrincipal.setEditable(false);
        txtPaginaWeb.setEditable(false);
    }

    public void activarTextField() {
        txtCodigoProveedor.setEditable(true);
        txtNITProveedor.setEditable(true);
        txtNombresProveedor.setEditable(true);
        txtApellidosProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtContactoPrincipal.setEditable(true);
        txtPaginaWeb.setEditable(true);
    }

    public void limpiarTextField() {
        txtCodigoProveedor.clear();
        txtNITProveedor.clear();
        txtNombresProveedor.clear();
        txtApellidosProveedor.clear();
        txtDireccionProveedor.clear();
        txtRazonSocial.clear();
        txtContactoPrincipal.clear();
        txtPaginaWeb.clear();
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
