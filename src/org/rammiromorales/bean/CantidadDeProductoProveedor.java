/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.bean;


public class CantidadDeProductoProveedor {
    private int codigoCantidadProveedor;
    private String cantidadProductoProveedor;

    public CantidadDeProductoProveedor() {
    }

    public CantidadDeProductoProveedor(int codigoCantidadProveedor, String cantidadProductoProveedor) {
        this.codigoCantidadProveedor = codigoCantidadProveedor;
        this.cantidadProductoProveedor = cantidadProductoProveedor;
    }
    
    public int getCodigoCantidadProveedor() {
        return codigoCantidadProveedor;
    }

    public void setCodigoCantidadProveedor(int codigoCantidadProveedor) {
        this.codigoCantidadProveedor = codigoCantidadProveedor;
    }

    public String getCantidadProductoProveedor() {
        return cantidadProductoProveedor;
    }

    public void setCantidadProductoProveedor(String cantidadProductoProveedor) {
        this.cantidadProductoProveedor = cantidadProductoProveedor;
    }

}
