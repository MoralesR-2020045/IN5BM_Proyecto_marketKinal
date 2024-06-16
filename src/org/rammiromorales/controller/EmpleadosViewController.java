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
import org.rammiromorales.bean.CargoEmpleado;
import org.rammiromorales.bean.Empleado;
import org.rammiromorales.bean.Producto;
import org.rammiromorales.bean.TipoProducto;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.report.GenerarReportes;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author Donovan Morales
 */
public class EmpleadosViewController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<Empleado> listaEmpleado;
    private ObservableList<CargoEmpleado> listaDeCargo;
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
    private MenuItem btnMenuPrincipal;

    @FXML
    private MenuItem btnClientes;

    @FXML
    private MenuItem btnProgramador;

    @FXML
    private TableView tvlEmpleados;

    @FXML
    private TableColumn colCodigoEmpleado;

    @FXML
    private TableColumn colNombresEmpleado;

    @FXML
    private TableColumn colApellidosEmpleado;

    @FXML
    private TableColumn colSueldo;

    @FXML
    private TableColumn colDireccion;

    @FXML
    private TableColumn colTurno;

    @FXML
    private TableColumn colCodigoCargoEmpleado;

    @FXML
    private TextField txtNombresEmpleado;

    @FXML
    private TextField txtApellidosEmpleado;

    @FXML
    private TextField txtSueldo;

    @FXML
    private TextField txtCodigoEmpleado;

    @FXML
    private TextField txtTurno;

    @FXML
    private TextField txtDireccion;

    @FXML
    private ComboBox cmbCodigoCargoEmpleado;
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
        tvlEmpleados.setItems(listaEmpleado);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaDatos();
        cmbCodigoCargoEmpleado.setItems(listaDeCargoEmpleado());
    }

    public void cargaDatos() {
        tvlEmpleados.setItems(getProducto());

        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleado"));
        colNombresEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombresEmpleado"));
        colApellidosEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidosEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleado, String>("turno"));
        colCodigoCargoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoCargoEmpleado"));
    }

    public void selecionarElementos() {
        txtCodigoEmpleado.setText(String.valueOf(((Empleado) tvlEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNombresEmpleado.setText(((Empleado) tvlEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApellidosEmpleado.setText(((Empleado) tvlEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
        txtSueldo.setText(String.valueOf(((Empleado) tvlEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtTurno.setText(((Empleado) tvlEmpleados.getSelectionModel().getSelectedItem()).getTurno());
        txtDireccion.setText(((Empleado) tvlEmpleados.getSelectionModel().getSelectedItem()).getDireccion());
        cmbCodigoCargoEmpleado.getSelectionModel().select(buscarEmpleado(((Empleado) tvlEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
    }

    public CargoEmpleado buscarEmpleado(int codigoTipoEmpleado) {
        CargoEmpleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarCargoEmpleado(?)}");
            procedimiento.setInt(1, codigoTipoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new CargoEmpleado(registro.getInt("codigoCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<Empleado> getProducto() {
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("call sp_listarEmpleados()");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleado(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleado = FXCollections.observableArrayList(lista);
    }

    public ObservableList<CargoEmpleado> listaDeCargoEmpleado() {
        ArrayList<CargoEmpleado> listado = new ArrayList<>();
        try {
            PreparedStatement consulta = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCargoEmpleado()}");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                listado.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                ));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return listaDeCargo = FXCollections.observableList(listado);
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
        ObservableList<Empleado> listaFiltrada = FXCollections.observableArrayList();
        if (filtro.isEmpty()) {
            listaFiltrada.addAll(listaEmpleado);
        } else {
            for (Empleado empleado : listaEmpleado) {
                if (String.valueOf(empleado.getCodigoEmpleado()).toLowerCase().contains(filtro)
                        || empleado.getNombresEmpleado().toLowerCase().contains(filtro)
                        || empleado.getApellidosEmpleado().toLowerCase().contains(filtro)
                        || String.valueOf(empleado.getSueldo()).toLowerCase().contains(filtro)
                        || empleado.getDireccion().toLowerCase().contains(filtro)
                        || empleado.getTurno().toLowerCase().contains(filtro)
                        || String.valueOf(empleado.getCodigoCargoEmpleado()).toLowerCase().contains(filtro)) {
                    listaFiltrada.add(empleado);
                }
            }
        }
        tvlEmpleados.setItems(listaFiltrada);
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
        String png = "png";
        Empleado registro = new Empleado();
        registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmpleado.getText()));
        registro.setNombresEmpleado(txtNombresEmpleado.getText());
        registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setDireccion(txtDireccion.getText());
        registro.setTurno(txtTurno.getText());
        registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarEmpleados(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();
            listaEmpleado.add(registro);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                btnMultiple.setStyle("");
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvlEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro ", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarEmpleados(?)}");
            procedimiento.setInt(1, ((Empleado) tvlEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            procedimiento.execute();
            listaEmpleado.remove(tvlEmpleados.getSelectionModel().getSelectedItem());
            cargaDatos();
            desactivarControles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                btnMultiple.setStyle("");
                if (tvlEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnMultiple.setStyle("    -fx-border-color: black;\n"
                            + "    -fx-background-radius: 10;\n"
                            + "    -fx-border-radius: 10;\n"
                            + "    -fx-background-radius: #FFFFFF;\n"
                            + "    -fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #F28C0F, #FE492C);");
                    activarControles();
                    txtCodigoEmpleado.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarEmpleados(?, ?, ?, ?, ?, ?, ?)}");
            String png = "png";
            Empleado registro = ((Empleado) tvlEmpleados.getSelectionModel().getSelectedItem());
            registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmpleado.getText()));
            registro.setNombresEmpleado(txtNombresEmpleado.getText());
            registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccion.getText());
            registro.setTurno(txtTurno.getText());
            registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
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
                tipoDeOperaciones = operaciones.NINGUNO;

                break;
        }
    }

    public void imprimirReporte() {
        Map parametros = new HashMap();
        parametros.put("codigoEmpleado", null);
        GenerarReportes.mostrarReportes("ReporteEmpleados.jasper", "Reporte Empleado", parametros);
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtNombresEmpleado.setEditable(false);
        txtApellidosEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
        txtCodigoEmpleado.setEditable(false);
        txtTurno.setEditable(false);
        txtDireccion.setEditable(false);
        cmbCodigoCargoEmpleado.setDisable(true);
    }

    public void activarControles() {
        txtNombresEmpleado.setEditable(true);
        txtApellidosEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        txtCodigoEmpleado.setEditable(true);
        txtTurno.setEditable(true);
        txtDireccion.setEditable(true);
        cmbCodigoCargoEmpleado.setDisable(false);
    }

    public void limpiarControles() {
        txtNombresEmpleado.clear();
        txtApellidosEmpleado.clear();
        txtSueldo.clear();
        txtCodigoEmpleado.clear();
        txtTurno.clear();
        txtDireccion.clear();
        cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem();
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
            tvlEmpleados.setPrefHeight(402);
            tvlEmpleados.setLayoutY(143);
            ancherPane.setVisible(true);
            controlDeButton = null;
        } else {
            tvlEmpleados.setPrefHeight(213);
            tvlEmpleados.setLayoutY(332);
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
                int Confirma = JOptionPane.showConfirmDialog(null, "Desea Cancelar el proceso de Agregar un Empleado", " Cancerlar ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
                int Confirma = JOptionPane.showConfirmDialog(null, "Desea Cancelar el proceso de Editar un Empleado", " Cancerlar ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (Confirma == JOptionPane.YES_NO_OPTION) {
                    limpiarControles();
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha cancelado puedes seguir editando Empleados");
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }
                break;
        }
    }

    public void Principal() {
        escenarioPrincipal.ventanaMenuPrincipal();
    }
    
    public void login(){
        escenarioPrincipal.ventanaLoginPrincipal();
    }
    
    public void cargoEmpleado(){
        escenarioPrincipal.ventanaCargoEmpleado();
    }
}
