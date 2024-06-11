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
import org.rammiromorales.bean.Producto;
import org.rammiromorales.bean.ProductoProveedor;
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
    private ObservableList<ProductoProveedor> listaProductoProveedor;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO

    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnListar;
    
        @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;

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
    @FXML
    private ComboBox cmbNombreProducto;
    @FXML
    private TableColumn colNombreProducto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaDatos();
        cmbProducto.setItems(listaDeTipoProducto());
        cmbProveedor.setItems(listaDeProveedores());
        cmbNombreProducto.setItems(listaProductoProveedor());
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
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("idProductoProveedor"));
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
        cmbProducto.getSelectionModel().select(buscarTipoProducto(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        cmbProveedor.getSelectionModel().select(buscarProveedor(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        cmbNombreProducto.getSelectionModel().select(buscarNombreProveedor(((Producto) tblProductos.getSelectionModel().getSelectedItem()).getIdProductoProveedor()));
    }

    public ProductoProveedor buscarNombreProveedor(int codigoProductoProveedor) {
        ProductoProveedor resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarProductoProveedor(?)}");
            procedimiento.setInt(1, codigoProductoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new ProductoProveedor(registro.getInt("idProductoProveedor"),
                        registro.getString("nombreProductoProveedor"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioProveedor"),
                        registro.getInt("cantidadDeProducto"),
                        registro.getInt("existenciaPorDescripcion"),
                        registro.getInt("existenciaTotalDelProducto")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public TipoProducto buscarTipoProducto(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                        registro.getString("descripcion")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public Proveedores buscarProveedor(int codigoTipoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarProveedores(?)}");
            procedimiento.setInt(1, codigoTipoProveedor);
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
                        resultado.getInt("idProductoProveedor"),
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

        return listaProductoProveedor = FXCollections.observableList(listado);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
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
        double cero = 0.0;
        int  ceros=0;
        Producto registro = new Producto();
        registro.setCodigoProducto(txtCodigoProducto.getText());
        registro.setIdProductoProveedor(((ProductoProveedor) cmbNombreProducto.getSelectionModel().getSelectedItem()).getIdProductoProveedor());
        registro.setCodigoProveedor(((Proveedores) cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCodigoTipoProducto(((TipoProducto) cmbProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
        registro.setDescripcionProducto((txtDescripcionProducto.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarProductos(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, cero);
            procedimiento.setDouble(4, cero);
            procedimiento.setDouble(5, cero);
            procedimiento.setString(6, png);
            procedimiento.setInt(7, ceros);
            procedimiento.setInt(8, registro.getIdProductoProveedor());
            procedimiento.setInt(9, registro.getCodigoTipoProducto());
            procedimiento.setInt(10, registro.getCodigoProveedor());
            procedimiento.execute();

            listaProducto.add(registro);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void eliminarProveedores() {
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro ", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarProductos(?)}");
            procedimiento.setString(1, ((Producto) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
            procedimiento.execute();
            listaProducto.remove(tblProductos.getSelectionModel().getSelectedItem());
            desactivarControles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText(" Actualizar ");
                    btnListar.setText("Cancelar ");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtCodigoProducto.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarProductos(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            String png = "png";
            Producto registro = ((Producto) tblProductos.getSelectionModel().getSelectedItem());
            registro.setCodigoProducto(txtCodigoProducto.getText());
            registro.setDescripcionProducto((txtDescripcionProducto.getText()));
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
            registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
            registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
            registro.setIdProductoProveedor(((ProductoProveedor) cmbNombreProducto.getSelectionModel().getSelectedItem()).getIdProductoProveedor());
            registro.setCodigoProveedor(((Proveedores) cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setCodigoTipoProducto(((TipoProducto) cmbProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());

            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, png);
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getIdProductoProveedor());
            procedimiento.setInt(9, registro.getCodigoTipoProducto());
            procedimiento.setInt(10, registro.getCodigoProveedor());
            procedimiento.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
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

    public void desactivarControles() {
        txtCodigoProducto.setEditable(false);
        txtDescripcionProducto.setEditable(false);
        cmbProducto.setDisable(true);
        cmbProveedor.setDisable(true);
        cmbNombreProducto.setDisable(true);
    }

    public void activarControles() {
        txtCodigoProducto.setEditable(true);
        txtDescripcionProducto.setEditable(true);
        cmbProducto.setDisable(false);
        cmbProveedor.setDisable(false);
        cmbNombreProducto.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoProducto.clear();
        txtDescripcionProducto.clear();
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
