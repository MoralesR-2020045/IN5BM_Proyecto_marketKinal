-- Nombre: Ramiro Donovan Morales 
-- Codigo Academico: IN5BM 
-- Carne: 2020045
-- Fecha de Creacion: 22/04/2024

drop database if exists DBKinalStore;

create database DBKinalStore;

use DBKinalStore;

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin15';
create table CantidadDeProdutoProveedor(
	codigoCantidadProveedor int auto_increment not null, 
    cantidadProductoProveedor varchar(45),
    primary key PK_CantidadDeProductoProveedor(codigoCantidadProveedor)
);

create table ProductoProveedor(
	idProductoProveedor int auto_increment,
    nombreProductoProveedor varchar(60),
	descripcionProducto varchar(100),
    precioProveedor decimal(10,2),
    cantidadDeProducto int,
    existenciaPorDescripcion int,
    existenciaTotalDelProducto int,
    primary key PK_IdProductoProveedor(idProductoProveedor)
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

create table EmailProveedor(
	codigoEmailProveedor int not null,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    primary key PK_EmailProveedor(codigoEmailProveedor),
    constraint FK_EmailProveedor_Proveedores foreign key EmailProveedor(codigoProveedor)
	references Proveedores(codigoProveedor) on delete cascade
);

create table TelefonoProveedor(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    primary key PK_TelefonoProveedor(codigoTelefonoProveedor),
    constraint FK_TelefonoProveedor_Proveedores foreign key TelefonoProveedor(codigoProveedor)
	references Proveedores(codigoProveedor) on delete cascade
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
	codigoCargoEmpleado int auto_increment ,
    nombreCargo varchar (45),
    descripcionCargo varchar (45),
    primary key PK_codigoCargoEmpleado (codigoCargoEmpleado)
);

create table TipoProducto(
	codigoTipoProducto int auto_increment,
    -- precioProductoDeProveedor int , 
	-- unidadesDeProducto int,
    -- codigoCantidadProveedor int,
    descripcion varchar (45),
    primary key PK_TipoProducto(codigoTipoProducto) 
    -- constraint FK_TipoProducto_CantidadDeProdutoProveedor foreign key TipoProducto(codigoCantidadProveedor)
    -- references CantidadDeProdutoProveedor(codigoCantidadProveedor) on delete cascade
);

create table Productos(
	codigoProducto varchar(15) not null,
    descripcionProducto varchar(45),
	precioUnitario decimal(10,2),
	precioDocena decimal(10,2),
	precioMayor decimal(10,2),
	imagenProducto varchar(45),
	existencia int,
    idProductoProveedor int,
    codigoTipoProducto int,
    codigoProveedor int,

    primary key PK_codigoProducto (codigoProducto),
    
    constraint FK_Productos_IdProductoProveedor foreign key Productos(idProductoProveedor) 
    references ProductoProveedor(idProductoProveedor) on delete cascade,
    
    constraint FK_Productos_TipoProducto foreign key Productos(codigoTipoProducto) 
    references TipoProducto(codigoTipoProducto) on delete cascade,
    
    constraint FK_Productos_Proveedores foreign key Productos(codigoProveedor) 
    references Proveedores(codigoProveedor) on delete cascade
);

create table Empleados(
	codigoEmpleado int not null,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    codigoCargoEmpleado int,
    primary key PK_Empleados(codigoEmpleado),
    constraint FK_Empleados_CargoEmpleado foreign key Empleados(codigoCargoEmpleado)
	references CargoEmpleado(codigoCargoEmpleado) on delete cascade
);



create table DetalleCompra(
	codigoDetalleCompra int not null,
    costoUnitario decimal(10,2),
    cantidad int,
    codigoProducto varchar(15),
    numeroDocumento int,
    primary key PK_DetalleCompra(codigoDetalleCompra),
    constraint FK_DetalleCompra_Productos foreign key DetalleCompra(codigoProducto)
		references Productos(codigoProducto) on delete cascade,
	constraint FK_DetalleCompra_Compras foreign key DetalleCompra(numeroDocumento)
		references	Compras(numeroDocumento) on delete cascade
);

create table Factura(
	numeroFactura int not null,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura date,
    codigoCliente int,
    codigoEmpleado int,
    primary key PK_Factura(numeroFactura),
    constraint FK_Factura_Clientes foreign key Factura(codigoCliente)
		references Clientes(codigoCliente) on delete cascade,
	constraint FK_Factura_Empleados foreign key Factura(codigoEmpleado)
		references Empleados(codigoEmpleado) on delete cascade
);

create table DetalleFactura(
	codigoDetalleFactura int not null,
    precioUnitario decimal(10,2),
    cantidad int,
    numeroFactura int,
    codigoProducto varchar(15),
    primary key PK_DetalleFactura(codigoDetalleFactura),
    constraint FK_DetalleFactura_Factura foreign key DetalleFactura(numeroFactura)
		references Factura(numeroFactura)on delete cascade,
	constraint FK_DetalleFactura foreign key DetalleFactura(codigoProducto)
		references Productos(codigoProducto) on delete cascade
);

Create Table Usuarios(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar(100) not null,
    apellidoUsuario varchar(100) not null,
    usuarioLogin varchar(50) not null,
    contrasena varchar(50) not null,
    primary key PK_codigoUsuario (codigoUsuario)
);

Create Table Login{
	usuarioMaster varchar(50) not null,
    passwordLogin varchar(50) not null,
    primary key PK_usuarioMaster (usuarioMaster)
}

-- Listar y agregar
Delimiter $$
	Create procedure sp_AgregarUsuario(in nombreUsuarioA varchar(100), in apellidoUsuarioA varchar(100),
		in usuarioLoginA varchar(50), in contrasenaA varchar(50))
        Begin
			Insert into Usuarios(nombreUsuario, apellidoUsuario, usuarioLogin, contrasena)
				values(nombreUsuarioA, apellidoUsuarioA, usuarioLoginA, contrasenaA);
        End$$
Delimiter ;

Delimiter $$
	Create procedure sp_ListarUsuarios()
		Begin
			Select
				U.codigoUsuario,
                U.nombreUsuario,
                U.apellidoUsuario,
                U.usuarioLogin,
                U.contrasena
                from Usuarios as U;
        End$$
Delimiter ;

