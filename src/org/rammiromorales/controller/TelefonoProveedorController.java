/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private Principal escenarioPrincipal;
    private ObservableList<TelefonoProveedor> listaTelefonos;
    private ObservableList<Proveedores> listaDeProveedores;

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
