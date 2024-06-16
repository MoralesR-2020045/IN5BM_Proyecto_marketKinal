/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.rammiromorales.database.Conexion;

/**
 *
 * @author informatica
 */
public class GenerarReportes {

    public static void mostrarReportes(String nombreReporte, String titulo, Map parametros) {
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try {
            JasperReport reporteCliente = (JasperReport)JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteCliente,parametros,Conexion.getInstancia().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}


/*
Interfaces Map 
    HashMap es uno de los objetos que implementa un conjunto de key-value.
    Tiene un constructor sin parametros new hashMap() y su finalidad suele referirse para agruparse
    informacion en un unico objeto
    Tiene cierta simulitud con la coleccion de objetos (ArrayList) pero con la
diferencia que estos no tiene orden

Hash hace referencia a una tecnica de organizacion de archivos, hashing (abierto-cerrado) en la
se almacena registro en una direccion que es generada por una funcion se aplica a la llave del


En java el HasMap posee un espacion de memoria y cuando se guarda un objeto en dicho espacio
se determina su direccion aplicando una funcion a la llave que le indica 
    
    

*/
