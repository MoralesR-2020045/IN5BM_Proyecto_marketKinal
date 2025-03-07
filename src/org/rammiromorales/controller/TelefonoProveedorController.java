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
import static javafx.collections.FXCollections.observableList;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import org.rammiromorales.bean.EmailProveedor;
import org.rammiromorales.bean.Proveedores;
import org.rammiromorales.bean.TelefonoProveedor;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author Donovan Morales
 */
public class TelefonoProveedorController implements Initializable {

    private ObservableList<TelefonoProveedor> listaTelefono;
    private Principal escenarioPrincipal;
    private ObservableList<TelefonoProveedor> listaTelefonos;
    private ObservableList<Proveedores> listaDeProveedores;
    private Button controlDeButton;
    private String accion;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO

    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
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
    private TextField txtCodigoTelefonoProveedor;

    @FXML
    private TextField txtNumeroPrincipal;

    @FXML
    private TextField txtNumeroSecundario;

    @FXML
    private MenuItem btnPrincipal;

    @FXML
    private MenuItem btnClientes;

    @FXML
    private MenuItem btnProgramador;

    @FXML
    private TableView tvlTelefonoProveedor;

    @FXML
    private TableColumn colCodigoTelefonoProveedor;

    @FXML
    private TableColumn colNumeroPrincipal;

    @FXML
    private TableColumn colNumeroSecundario;

    @FXML
    private TableColumn colObservaciones;

    @FXML
    private TableColumn colCodigoProveedor1;

    @FXML
    private ComboBox cmbCodigoProveedor;

    @FXML
    private TextField txtObservaciones;
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
        tvlTelefonoProveedor.setItems(listaTelefonos);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaDatos();
        cmbCodigoProveedor.setItems(listaDeProveedores());
    }

    public void cargaDatos() {
        tvlTelefonoProveedor.setItems(getTelefonoProveedor());
        colCodigoTelefonoProveedor.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoTelefonoProveedor"));
        colNumeroPrincipal.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroPrincipal"));
        colNumeroSecundario.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroSecundario"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("observaciones"));
        colCodigoProveedor1.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoProveedor"));
    }

    public ObservableList<TelefonoProveedor> getTelefonoProveedor() {
        ArrayList<TelefonoProveedor> lista = new ArrayList<TelefonoProveedor>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("call sp_listarTelefonoProveedor()");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TelefonoProveedor(resultado.getInt("codigoTelefonoProveedor"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTelefonos = FXCollections.observableArrayList(lista);
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

    @FXML
    private void buscar(KeyEvent event) {
        String filtro = txtBuscar.getText().toLowerCase().trim();
        filtrarDatos(filtro);
    }

    private void filtrarDatos(String filtro) {
        listaTelefono = FXCollections.observableArrayList();
        if (filtro.isEmpty()) {
            listaTelefono.addAll(listaTelefonos);
        } else {
            for (TelefonoProveedor telefonoProveedor : listaTelefonos) {
                if (String.valueOf(telefonoProveedor.getCodigoTelefonoProveedor()).toLowerCase().contains(filtro)
                        || telefonoProveedor.getNumeroPrincipal().toLowerCase().contains(filtro)
                        || telefonoProveedor.getNumeroSecundario().toLowerCase().contains(filtro)
                        || telefonoProveedor.getObservaciones().toLowerCase().contains(filtro)
                        || String.valueOf(telefonoProveedor.getCodigoProveedor()).toLowerCase().contains(filtro)) {
                    listaTelefono.add(telefonoProveedor);
                }
            }
        }
        tvlTelefonoProveedor.setItems(listaTelefono);
    }

    public void selecionarElementos() {
        txtCodigoTelefonoProveedor.setText(String.valueOf(((TelefonoProveedor) tvlTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
        txtNumeroPrincipal.setText(((TelefonoProveedor) tvlTelefonoProveedor.getSelectionModel().getSelectedItem()).getNumeroPrincipal());
        txtNumeroSecundario.setText(((TelefonoProveedor) tvlTelefonoProveedor.getSelectionModel().getSelectedItem()).getNumeroSecundario());
        txtObservaciones.setText(((TelefonoProveedor) tvlTelefonoProveedor.getSelectionModel().getSelectedItem()).getObservaciones());
        cmbCodigoProveedor.getSelectionModel().select(buscarEmpleado(((TelefonoProveedor) tvlTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }

    public Proveedores buscarEmpleado(int codigoTipoEmpleado) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarProveedores(?)}");
            procedimiento.setInt(1, codigoTipoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITProveedor"),
                        registro.getString("nombresProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                btnMultiple.setStyle("    -fx-border-color: black;\n"
                        + "    -fx-background-radius: 10;\n"
                        + "    -fx-border-radius: 10;\n"
                        + "    -fx-background-radius: #FFFFFF;\n"
                        + "    -fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #F28C0F, #FE492C);");
                activarControles();
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                btnMultiple.setStyle(" ");
                guardar();
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargaDatos();
                break;
        }
    }

    public void guardar() {
        String png = "png";
        TelefonoProveedor registro = new TelefonoProveedor();
        registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoTelefonoProveedor.getText()));
        registro.setNumeroPrincipal(txtNumeroPrincipal.getText());
        registro.setNumeroSecundario(txtNumeroSecundario.getText());
        registro.setObservaciones(txtObservaciones.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarTelefonoProveedor(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
            listaTelefonos.add(registro);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvlTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro ", "Eliminar Telefono Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confirmacion == JOptionPane.YES_NO_OPTION) {
                        eliminarProceso();
                        limpiarControles();
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarTelefonoProveedor(?)}");
            procedimiento.setInt(1, ((TelefonoProveedor) tvlTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
            procedimiento.execute();
            listaTelefonos.remove(tvlTelefonoProveedor.getSelectionModel().getSelectedItem());
            cargaDatos();
            desactivarControles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvlTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnMultiple.setStyle("    -fx-border-color: black;\n"
                            + "    -fx-background-radius: 10;\n"
                            + "    -fx-border-radius: 10;\n"
                            + "    -fx-background-radius: #FFFFFF;\n"
                            + "    -fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #F28C0F, #FE492C);");
                    activarControles();
                    txtCodigoTelefonoProveedor.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un proveedor para editar ");
                }
                break;
            case ACTUALIZAR:
                btnMultiple.setStyle(" ");
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarTelefonoProveedor(?, ?, ?, ?, ?)}");
            TelefonoProveedor registro = ((TelefonoProveedor) tvlTelefonoProveedor.getSelectionModel().getSelectedItem());
            registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoTelefonoProveedor.getText()));
            registro.setNumeroPrincipal(txtNumeroPrincipal.getText());
            registro.setNumeroSecundario(txtNumeroSecundario.getText());
            registro.setObservaciones(txtObservaciones.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtCodigoTelefonoProveedor.setEditable(false);
        txtNumeroPrincipal.setEditable(false);
        txtNumeroSecundario.setEditable(false);
        txtObservaciones.setEditable(false);
        cmbCodigoProveedor.setDisable(true);
    }

    public void activarControles() {
        txtCodigoTelefonoProveedor.setEditable(true);
        txtNumeroPrincipal.setEditable(true);
        txtNumeroSecundario.setEditable(true);
        txtObservaciones.setEditable(true);
        cmbCodigoProveedor.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoTelefonoProveedor.clear();
        txtNumeroPrincipal.clear();
        txtNumeroSecundario.clear();
        txtObservaciones.clear();
        cmbCodigoProveedor.getSelectionModel().getSelectedItem();
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
            tvlTelefonoProveedor.setPrefHeight(408);
            tvlTelefonoProveedor.setLayoutY(139);
            ancherPane.setVisible(true);
            controlDeButton = null;
        } else {
            tvlTelefonoProveedor.setPrefHeight(229);
            tvlTelefonoProveedor.setLayoutY(318);
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
                int Confirma = JOptionPane.showConfirmDialog(null, "Desea Cancelar el proceso de Agregar un Numero de Telefono", " Cancerlar ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
                int Confirma = JOptionPane.showConfirmDialog(null, "Desea Cancelar el proceso de Editar un Numero de Telefono ", " Cancerlar ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (Confirma == JOptionPane.YES_NO_OPTION) {
                    limpiarControles();
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha cancelado puedes seguir Editando");
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }
                break;
        }
    }

    
    public void principal() {
        escenarioPrincipal.ventanaMenuPrincipal();
    }
    public void proveedor(){
        escenarioPrincipal.ventanaProveedores();
    }
    public void login(){
        escenarioPrincipal.ventanaLoginPrincipal();
    }
}
