package org.rammiromorales.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Connection conexion;
    private static Conexion instancia;
    private String baseDatos = "jdbc:mysql://localhost:3306/DBKinalStore?userSSL=false";
    private String useCasa = "root";
    private String useColegio = "2023021_IN5BV";
    private String passwordCasa = "admin15";
    private String passwordColegio = "abc123!!";
    
    public Conexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection(baseDatos,useColegio,passwordColegio);
        
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(SQLException  e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Conexion getInstancia() {
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}
