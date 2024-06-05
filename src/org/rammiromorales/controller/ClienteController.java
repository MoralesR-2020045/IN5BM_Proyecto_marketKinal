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
import org.rammiromorales.bean.Clientes;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.report.GenerarReportes;
import org.rammiromorales.system.Principal;

/**
 *
 * @author informatica
 */
public class ClienteController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<Clientes> listaClientes;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

// Tabla y columnas de la misma 
    @FXML
    private TableView tvClientes;

    @FXML
    private TableColumn colCodigoC;

    @FXML
    private TableColumn colNombresC;

    @FXML
    private TableColumn colApellidosC;

    @FXML
    private TableColumn colDireccionC;

    @FXML
    private TableColumn colNitC;

    @FXML
    private TableColumn colTelefonoC;

    @FXML
    private TableColumn colCorreoC;

// Text field que reciben datos 
    @FXML
    private TextField txtCodigoC;

    @FXML
    private TextField txtNombresC;

    @FXML
    private TextField txtApellidosC;

    @FXML
    private TextField txtDireccionC;

    @FXML
    private TextField txtTelefonoC;

    @FXML
    private TextField txtCorreoC;

    @FXML
    private TextField txtNitC;

// Botones que sirven para retorno o cumplir siertas acciones 
    @FXML
    private Button btnAgregarC;
    @FXML
    private Button btnEliminarC;
    @FXML
    private Button btnEditarC;
    @FXML
    private Button btnListarC;
    @FXML
    private MenuItem btnMenu;
    @FXML
    private MenuItem btnClientes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargarDatos() {
        tvClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNitC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITCliente"));
        colNombresC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombresCliente"));
        colApellidosC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidosCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }

    public void seleccionarDatos() {
        txtCodigoC.setText(String.valueOf(((Clientes) tvClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNombresC.setText(((Clientes) tvClientes.getSelectionModel().getSelectedItem()).getNombresCliente());
        txtApellidosC.setText(((Clientes) tvClientes.getSelectionModel().getSelectedItem()).getApellidosCliente());
        txtDireccionC.setText(((Clientes) tvClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtTelefonoC.setText(((Clientes) tvClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtCorreoC.setText(((Clientes) tvClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());
        txtNitC.setText(((Clientes) tvClientes.getSelectionModel().getSelectedItem()).getNITCliente());

    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCliente()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("NITCliente"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableList(lista);
    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarC.setText("Guardar");
                btnEliminarC.setText("Cancelar");
                btnEditarC.setDisable(true);
                btnListarC.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;

                break;
            case ACTUALIZAR:
                guardar();
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregarC.setText("Agregar");
                btnEliminarC.setText("Eliminar");
                btnEditarC.setDisable(false);
                btnListarC.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Clientes registro = new Clientes();
        registro.setNITCliente(txtNitC.getText());
        registro.setNombresCliente(txtNombresC.getText());
        registro.setApellidosCliente(txtApellidosC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setCorreoCliente(txtCorreoC.getText());
        try {
            // la variable procedimiento es global aca por la razon de que es 
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarCliente(?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNITCliente());
            procedimiento.setString(2, registro.getNombresCliente());
            procedimiento.setString(3, registro.getApellidosCliente());
            procedimiento.setString(4, registro.getDireccionCliente());
            procedimiento.setString(5, registro.getTelefonoCliente());
            procedimiento.setString(6, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarC.setText("Agregar");
                btnEliminarC.setText("Eliminar");
                btnEditarC.setDisable(false);
                btnListarC.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarCliente(?)}");
                            procedimiento.setInt(1, ((Clientes) tvClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tvClientes.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para eliminar");
                    }
                    break;
                }
        }
    }

    // lleva el mismo concepto de agregar y eliminar
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditarC.setText(" Actualizar ");
                    btnListarC.setText("Cancelar ");
                    btnAgregarC.setDisable(true);
                    btnEliminarC.setDisable(true);
                    activarControles();
                    txtCodigoC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar ");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarC.setText(" Editar ");
                btnListarC.setText("Reporte ");
                btnAgregarC.setDisable(true);
                btnEliminarC.setDisable(true);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarCliente( ?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = ((Clientes) tvClientes.getSelectionModel().getSelectedItem());
            registro.setNITCliente(txtNitC.getText());
            registro.setNombresCliente(txtNombresC.getText());
            registro.setApellidosCliente(txtApellidosC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITCliente());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getApellidosCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
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
                desactivarControles();
                limpiarControles();
                btnEditarC.setText("Actualizar");
                btnListarC.setText("Cancelar");
                btnAgregarC.setDisable(false);
                btnEliminarC.setDisable(false);
                btnEditarC.setText("Editar");
                btnListarC.setText("Reporte");
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void imprimirReporte() {
        Map parametros = new HashMap();
        parametros.put("codigoCliente", null);
        GenerarReportes.mostrarReportes("reportCliente.jasper", "Reporte Cliente", parametros);
    }

    public void desactivarControles() {
        txtCodigoC.setEditable(false);
        txtNombresC.setEditable(false);
        txtApellidosC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtTelefonoC.setEditable(false);
        txtCorreoC.setEditable(false);
        txtNitC.setEditable(false);
    }

    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtNombresC.setEditable(true);
        txtApellidosC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtTelefonoC.setEditable(true);
        txtCorreoC.setEditable(true);
        txtNitC.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoC.clear();
        txtNombresC.clear();
        txtApellidosC.clear();
        txtDireccionC.clear();
        txtTelefonoC.clear();
        txtCorreoC.clear();
        txtNitC.clear();
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenu) {
            escenarioPrincipal.ventanaMenuPrincipal();
        } else if (event.getSource() == btnClientes) {
            escenarioPrincipal.ventanaProgramador();
        }
    }
}
