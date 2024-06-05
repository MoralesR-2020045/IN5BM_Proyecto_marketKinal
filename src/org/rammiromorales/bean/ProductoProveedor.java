/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.bean;

/**
 *
 * @author Donovan Morales
 */
public class ProductoProveedor {
    
    private int idProductoProveedor; 
    private String nombreProductoProveedor;
    private String descripcionProducto; 
    private double precioProveedor;
    private int cantidadDeProducto; 
    private int existenciaPorDescripcion; 
    private int existenciaTotalDelProducto;

    public ProductoProveedor() {
    }

    public ProductoProveedor(int idProductoProveedor, String nombreProductoProveedor, String descripcionProducto, double precioProveedor, int cantidadDeProducto, int existenciaPorDescripcion, int existenciaTotalDelProducto) {
        this.idProductoProveedor = idProductoProveedor;
        this.nombreProductoProveedor = nombreProductoProveedor;
        this.descripcionProducto = descripcionProducto;
        this.precioProveedor = precioProveedor;
        this.cantidadDeProducto = cantidadDeProducto;
        this.existenciaPorDescripcion = existenciaPorDescripcion;
        this.existenciaTotalDelProducto = existenciaTotalDelProducto;
    }

    public int getIdProductoProveedor() {
        return idProductoProveedor;
    }

    public void setIdProductoProveedor(int idProductoProveedor) {
        this.idProductoProveedor = idProductoProveedor;
    }

    public String getNombreProductoProveedor() {
        return nombreProductoProveedor;
    }

    public void setNombreProductoProveedor(String nombreProductoProveedor) {
        this.nombreProductoProveedor = nombreProductoProveedor;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public double getPrecioProveedor() {
        return precioProveedor;
    }

    public void setPrecioProveedor(double precioProveedor) {
        this.precioProveedor = precioProveedor;
    }

    public int getCantidadDeProducto() {
        return cantidadDeProducto;
    }

    public void setCantidadDeProducto(int cantidadDeProducto) {
        this.cantidadDeProducto = cantidadDeProducto;
    }

    public int getExistenciaPorDescripcion() {
        return existenciaPorDescripcion;
    }

    public void setExistenciaPorDescripcion(int existenciaPorDescripcion) {
        this.existenciaPorDescripcion = existenciaPorDescripcion;
    }

    public int getExistenciaTotalDelProducto() {
        return existenciaTotalDelProducto;
    }

    public void setExistenciaTotalDelProducto(int existenciaTotalDelProducto) {
        this.existenciaTotalDelProducto = existenciaTotalDelProducto;
    }

       @Override
    public String toString() {
        return getIdProductoProveedor() + " | " + getNombreProductoProveedor();
    }
    
}
