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
import org.rammiromorales.bean.EmailProveedor;
import org.rammiromorales.bean.Empleado;
import org.rammiromorales.bean.Proveedores;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author Donovan Morales
 */
public class EmailProveedorController implements Initializable {

    private Principal escenarioPrincipal;

    private ObservableList<EmailProveedor> listaProveedorEmail;
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
    private TextField txtCodigoEmailProveedor;

    @FXML
    private TextField txtEmailProveedor;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private MenuItem btnPrincipal;

    @FXML
    private MenuItem btnClientes;

    @FXML
    private MenuItem btnProgramador;

    @FXML
    private TableView tvlEmailProveedor;

    @FXML
    private TableColumn colCodigoEmpleado;

    @FXML
    private TableColumn colNombreCargo;

    @FXML
    private TableColumn colDescripcionCargo;

    @FXML
    private TableColumn colCodigoProveedor;

    @FXML
    private ComboBox cmbCodigoProveedor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaDatos();
        cmbCodigoProveedor.setItems(listaDeProveedores());
    }

    public void cargaDatos() {
        tvlEmailProveedor.setItems(getProveedor());

        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("emailProveedor"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("descripcion"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));
    }

    public ObservableList<EmailProveedor> getProveedor() {
        ArrayList<EmailProveedor> lista = new ArrayList<EmailProveedor>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("call sp_listarEmailProveedor()");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new EmailProveedor(resultado.getInt("codigoEmailProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedorEmail = FXCollections.observableArrayList(lista);
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

    public void selecionarElementos() {
        txtCodigoEmailProveedor.setText(String.valueOf(((EmailProveedor) tvlEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
        txtEmailProveedor.setText(((EmailProveedor) tvlEmailProveedor.getSelectionModel().getSelectedItem()).getEmailProveedor());
        txtDescripcion.setText(((EmailProveedor) tvlEmailProveedor.getSelectionModel().getSelectedItem()).getDescripcion());
        cmbCodigoProveedor.getSelectionModel().select(buscarEmpleado(((EmailProveedor) tvlEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
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
        EmailProveedor registro = new EmailProveedor();
        registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmailProveedor.getText()));
        registro.setEmailProveedor(txtEmailProveedor.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarEmailProveedor(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
            listaProveedorEmail.add(registro);
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
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnListar.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvlEmailProveedor.getSelectionModel().getSelectedItem() != null) {
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarEmailProveedor(?)}");
            procedimiento.setInt(1, ((EmailProveedor) tvlEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
            procedimiento.execute();
            listaProveedorEmail.remove(tvlEmailProveedor.getSelectionModel().getSelectedItem());
            cargaDatos();
            desactivarControles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvlEmailProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText(" Actualizar ");
                    btnListar.setText("Cancelar ");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtCodigoEmailProveedor.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarEmailProveedor(?, ?, ?, ?)}");
            EmailProveedor registro = ((EmailProveedor) tvlEmailProveedor.getSelectionModel().getSelectedItem());
            registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmailProveedor.getText()));
            registro.setEmailProveedor(txtEmailProveedor.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
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
        txtCodigoEmailProveedor.setEditable(false);
        txtEmailProveedor.setEditable(false);
        txtDescripcion.setEditable(false);
        cmbCodigoProveedor.setDisable(true);
    }

    public void activarControles() {
        txtCodigoEmailProveedor.setEditable(true);
        txtEmailProveedor.setEditable(true);
        txtDescripcion.setEditable(true);
        cmbCodigoProveedor.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoEmailProveedor.clear();
        txtEmailProveedor.clear();
        txtDescripcion.clear();
        cmbCodigoProveedor.getSelectionModel().getSelectedItem();
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
