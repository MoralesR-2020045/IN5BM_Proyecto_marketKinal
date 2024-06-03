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
import org.rammiromorales.bean.TipoProducto;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author Donovan Morales
 */
public class TipoProductoViewController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<TipoProducto> listaDeTipoProducto;

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
    private TextField txtCodigoTipoProducto;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TableView tvlTipoProducto;

    @FXML
    private TableColumn  colCodigoTipoProducto;

    @FXML
    private TableColumn  colDescripcion;

    @FXML
    private Button btnInicio;

    @FXML
    private MenuItem btnClientes;

    @FXML
    private MenuItem btnProgramador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarDatosTable();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargarDatosTable() {
        tvlTipoProducto.setItems(listaDeTipoProducto());
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }

    public ObservableList<TipoProducto> listaDeTipoProducto() {
        ArrayList<TipoProducto> listador = new ArrayList<>();
        try {
            PreparedStatement consulta = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                listador.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return listaDeTipoProducto = FXCollections.observableList(listador);
    }

    // Metodos de Crud
    public void agregarTipoProducto() {
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
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setDescripcion(txtDescripcion.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarTipoProducto(?)}");
            procedimiento.setString(1, tipoProducto.getDescripcion());
            procedimiento.execute();
            listaDeTipoProducto.add(tipoProducto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarTipoProducto() {
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
                if (tvlTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro ", "Eliminar Tipo de Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
            procedimiento.setInt(1, ((TipoProducto) tvlTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
            procedimiento.execute();
            listaDeTipoProducto.remove(tvlTipoProducto.getSelectionModel().getSelectedItem());
            limpiarTextField();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarTipoProducto() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvlTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    btnActualizar.setText(" Actualizar ");
                    btnListar.setText("Cancelar ");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarTextField();
                    txtCodigoTipoProducto.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarTipoProducto(?, ?)}");
            TipoProducto tipoProducto = ((TipoProducto) tvlTipoProducto.getSelectionModel().getSelectedItem());
            tipoProducto.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1, tipoProducto.getCodigoTipoProducto());
            procedimiento.setString(2, tipoProducto.getDescripcion());
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
        txtCodigoTipoProducto.setEditable(false);
        txtDescripcion.setEditable(false);
    }

    public void activarTextField() {
        txtCodigoTipoProducto.setEditable(true);
        txtDescripcion.setEditable(true);
    }

    public void limpiarTextField() {
        txtCodigoTipoProducto.clear();
        txtDescripcion.clear();
    }


    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void handleButtonAction(ActionEvent event) {
        if  (event.getSource() == btnClientes) {
            escenarioPrincipal.ventanaMenuClientes();
        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.ventanaProgramador();
        }
    }
    
    public void inicio(){
        escenarioPrincipal.ventanaMenuPrincipal();
    }
    
    public void ventanaEmergente(){
    escenarioPrincipal.ventanaEmergenteTiporProducto();
    }

}
