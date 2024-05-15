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
import org.rammiromorales.bean.Producto;
import org.rammiromorales.bean.Proveedores;
import org.rammiromorales.bean.TipoProducto;
import org.rammiromorales.database.Conexion;
import org.rammiromorales.system.Principal;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class ProductoViewController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<Producto> listaProducto;
    private ObservableList<Proveedores> listaDeProveedores;
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
    private TextField txtCodigoProducto;

    @FXML
    private TextField txtDescripcionProducto;

    @FXML
    private TextField txtPrecioDocena;

    @FXML
    private TextField txtPrecioUnitario;

    @FXML
    private TextField txtPrecioMayor;

    @FXML
    private TextField txtImage;

    @FXML
    private TextField txtExistencia;

    @FXML
    private ComboBox cmbProducto;

    @FXML
    private ComboBox cmbProveedor;

    @FXML
    private TableColumn colCodigoProducto;

    @FXML
    private TableColumn coldescripcionProducto;

    @FXML
    private TableColumn colPrecioDocena;

    @FXML
    private TableColumn colPrecioUnitario;

    @FXML
    private TableColumn colPrecioMayor;

    @FXML
    private TableColumn colImagenProducto;

    @FXML
    private TableColumn colExistencia;

    @FXML
    private TableColumn colCodigoTipoProducto;

    @FXML
    private TableColumn colCodigoProveedor;
    @FXML
    private TableView tblProductos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaDatos();
        cmbProducto.setItems(listaDeTipoProducto());
        cmbProveedor.setItems(listaDeProveedores());
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void cargaDatos() {
        tblProductos.setItems(getProducto());

        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigoProducto"));
        coldescripcionProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioMayor"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("existencia"));
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("codigoTipoProducto"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("codigoProveedor"));
    }

    public void selecionarElementos() {
        txtCodigoProducto.setText(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
        txtDescripcionProducto.setText(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioUnitario.setText(String.valueOf(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioDocena.setText(String.valueOf(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioMayor.setText(String.valueOf(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtExistencia.setText(String.valueOf(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbProveedor.getSelectionModel().select(buscarTipoProducto(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
    }
    
        public TipoProducto buscarTipoProducto (int codigoTipoProducto ){
        TipoProducto resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
         procedimiento.setInt(1, codigoTipoProducto);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                                            registro.getString("descripcionProducto")
             
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }

// cargar lista getProducto
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

// cargar ListaProveedores
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

// Cargar lista 
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
        Producto registro = new Producto();
        registro.setCodigoProducto(txtCodigoProducto.getText());
        registro.setCodigoProveedor(((Proveedores) cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCodigoTipoProducto(((TipoProducto) cmbProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
        registro.setDescripcionProducto((txtDescripcionProducto.getText()));
        registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
        registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarProductos(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6,png );
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();

            listaProducto.add(registro);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoProducto.setEditable(false);
        txtDescripcionProducto.setEditable(false);
        txtPrecioUnitario.setEditable(false);
        txtPrecioDocena.setEditable(false);
        txtPrecioMayor.setEditable(false);
        txtExistencia.setEditable(false);
        cmbProducto.setDisable(true);
        cmbProveedor.setDisable(true);
    }

    public void activarControles() {
        txtCodigoProducto.setEditable(true);
        txtDescripcionProducto.setEditable(true);
        txtPrecioUnitario.setEditable(true);
        txtPrecioDocena.setEditable(true);
        txtPrecioMayor.setEditable(true);
        txtExistencia.setEditable(true);
        cmbProducto.setDisable(false);
        cmbProveedor.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoProducto.clear();
        txtDescripcionProducto.clear();
        txtPrecioUnitario.clear();
        txtPrecioDocena.clear();
        txtPrecioMayor.clear();
        txtExistencia.clear();
        tblProductos.getSelectionModel().getSelectedItem();
        cmbProducto.getSelectionModel().getSelectedItem();
        cmbProveedor.getSelectionModel().getSelectedItem();
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
