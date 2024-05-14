-- Nombre: Ramiro Donovan Morales 
-- Codigo Academico: IN5BM 
-- Carne: 2020045
-- Fecha de Creacion: 22/04/2024

drop database if exists DBKinalStore;

create database DBKinalStore;

use DBKinalStore;

create table TipoProducto(
	codigoTipoProducto int auto_increment,
    descripcion varchar (45),
    primary key PK_TipoProducto(codigoTipoProducto)
);

create table Compras(
	numeroDocumento int auto_increment,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    primary key PK_NumeroDocumento(numeroDocumento)
);

create table Proveedores (
	codigoProveedor int auto_increment, 
    NITProveedor varchar(10),
    nombresProveedor varchar(60),
    apellidosProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
	paginaWeb varchar(50),
    primary key PK_CodigoProveedor(codigoProveedor)
);

create table Clientes (
	codigoCliente int auto_increment,
    NITCliente varchar (10),
    nombresCliente varchar (50),
    apellidosCliente varchar (50),
    direccionCliente varchar(150),
    telefonoCliente varchar (45),
    correoCliente varchar (45),
    primary key PK_CodigoCliente(codigoCliente)
);

create table CargoEmpleado (
	codigoCargoEmpleado int auto_increment,
    nombreCargo varchar (45),
    descripcionCargo varchar (45),
    primary key PK_codigoCargoEmpleado (codigoCargoEmpleado)
);

create table Productos(
	codigoProducto varchar(15),
    descripcionProducto varchar(45),
	precioUnitario decimal(10,2),
	precioDocena decimal(10,2),
	precioMayor decimal(10,2),
	imagenProducto varchar(45),
	existencia int,
    codigoTipoProducto int,
    codigoProveedor int,
    foreign key (codigoProveedor) references Proveedores (codigoProveedor),
    foreign key (codigoTipoProducto) references TipoProducto (codigoTipoProducto),
    primary key PK_codigoProducto (codigoProducto)
);

