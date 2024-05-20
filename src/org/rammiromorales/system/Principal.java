/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.system;

import java.io.InputStream;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import org.rammiromorales.controller.CantidadDeProdutoProveedorController;
import org.rammiromorales.controller.CargoEmpleadoViewController;
import org.rammiromorales.controller.ClienteController;
import org.rammiromorales.controller.ComprasViewController;
import org.rammiromorales.controller.DetalleCompraViewController;
import org.rammiromorales.controller.EmailProveedorController;
import org.rammiromorales.controller.EmpleadosViewController;
import org.rammiromorales.controller.PrincipalController;
import org.rammiromorales.controller.ProductoViewController;
import org.rammiromorales.controller.ProgramadorController;
import org.rammiromorales.controller.ProveedoresViewController;
import org.rammiromorales.controller.TelefonoProveedorController;
import org.rammiromorales.controller.TipoProductoViewController;

/**
 *
 * @author informatica
 */
public class Principal extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private String paquete = "/org/rammiromorales/view/"
            + "";

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle(" Kinal Express");
        ventanaMenuPrincipal();
        escenarioPrincipal.setResizable(false);
        escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception {
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Principal.class.getResourceAsStream(paquete + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Principal.class.getResource(paquete + fxmlName));

        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void ventanaMenuPrincipal() {
        try {
            PrincipalController menuPrincipalView = (PrincipalController) cambiarEscena("PrincipalView.fxml", 340, 294);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ventanaMenuClientes() {
        try {
            ClienteController ventanaMenuClientes = (ClienteController) cambiarEscena("ClienteView.fxml", 1163, 654);
            ventanaMenuClientes.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
    }

    public void ventanaProgramador() {
        try {
            ProgramadorController vistaProgramador = (ProgramadorController) cambiarEscena("ProgramadorView.fxml", 746, 438);
            vistaProgramador.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ventanaCargoEmpleado() {
        try {
            CargoEmpleadoViewController vistaCargoEmpleado = (CargoEmpleadoViewController) cambiarEscena("CargoEmpleadoView.fxml", 1163, 654);
            vistaCargoEmpleado.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ventanaTipoProducto() {
        try {
            TipoProductoViewController vistaTipoProducto = (TipoProductoViewController) cambiarEscena("TipoProductoView.fxml", 1163, 654);
            vistaTipoProducto.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ventanaProveedores() {
        try {
            ProveedoresViewController proveedores = (ProveedoresViewController) cambiarEscena("ProveedoresView.fxml", 1163, 654);
            proveedores.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ventanaProducto() {
        try {
            ProductoViewController producto = (ProductoViewController) cambiarEscena("ProductoView.fxml", 1163, 654);
            producto.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ventanaCompras() {
        try {
            ComprasViewController compra = (ComprasViewController) cambiarEscena("ComprasView.fxml", 1163, 654);
            compra.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void cantidadProductoProveedor() {
        try {
            CantidadDeProdutoProveedorController cantidad = (CantidadDeProdutoProveedorController) cambiarEscena("CantidadDeProdutoProveedor.fxml", 1163, 654);
            cantidad.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ventanaDetalleProducto() {
        try {
            DetalleCompraViewController detalle = (DetalleCompraViewController) cambiarEscena("DetalleCompraView.fxml", 1163, 654);
            detalle.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ventanaEmpleados() {
        try {
            EmpleadosViewController detalle = (EmpleadosViewController) cambiarEscena("EmpleadosView.fxml", 1163, 654);
            detalle.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ventanaEmailProveedor() {
        try {
            EmailProveedorController detalle = (EmailProveedorController) cambiarEscena("EmailProveedor.fxml", 1163, 654);
            detalle.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ventanaTelefonoProveedor() {
        try {
            TelefonoProveedorController proveedor = (TelefonoProveedorController) cambiarEscena("TelefonoProveedor.fxml", 1163, 654);
            proveedor.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
