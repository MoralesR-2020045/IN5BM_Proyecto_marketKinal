-- DML Ramiro Donovan Morales López 
-- 2020045
-- Fecha de Creacion: 23/04/2024

use DBKinalStore;
set global time_zone = "-6:00";+

-- ------------------------------------------Entidad Cargo Empleado -----------------------------------------------------
-- -----------------------------------Procedimiento Agregar Cargo Empleado -------------------------------------------
Delimiter $$
	create procedure sp_agregarCargoEmpleado(in spNombreCargo varchar (45), in spDescripcionCargo varchar(45))
		begin 
			insert into CargoEmpleado(nombreCargo, descripcionCargo)
            values (spNombreCargo, spDescripcionCargo);
		end $$
Delimiter ;

call sp_agregarCargoEmpleado("Administrador", "llevar informe");


-- ------------------------------------------Procedimiento Listar Cargo Empleado -------------------------------------------
Delimiter $$
	create procedure sp_ListarCargoEmpleado ()
    begin
		select * from CargoEmpleado ;
    end $$
Delimiter ;

call sp_ListarCargoEmpleado();


-- ------------------------------------------Procedimiento Eliminar Cargo Empleado -------------------------------------------
Delimiter $$
	create procedure sp_eliminarCargoEmpleado (in spCodigoCargoEmpleado int)
		begin 
			delete from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = spCodigoCargoEmpleado;
		end $$
Delimiter ;

-- call sp_eliminarCargoEmpleado();

-- ------------------------------------------Procedimiento Actualizar Cargo Empleado -------------------------------------------
Delimiter $$
	create procedure  sp_actualizarCargoEmpleado (in spCodigoCargoEmpleado int, in spNombreCargo varchar (45), in spDescripcionCargo varchar(45))
        begin 
			update CargoEmpleado
            set
				nombreCargo = spNombreCargo,
                descripcionCargo = spDescripcionCargo
            where codigoCargoEmpleado = spCodigoCargoEmpleado;
		end $$
Delimiter ;

 -- call sp_actualizarCargoEmpleado(1, "D","DDS");


-- ------------------------------------------Entidad TIPO PRODUCTO -----------------------------------------------------
-- -----------------------------------Procedimiento Agregar Tipo Producto -------------------------------------------
Delimiter $$
	create procedure sp_agregarTipoProducto(in spDescripcion varchar (45))
		begin 
			insert into TipoProducto(descripcion)
            values (spDescripcion);
		end $$
Delimiter ;

call sp_agregarTipoProducto("xd");


-- ------------------------------------------View Listar Tipo Producto -------------------------------------------
Delimiter $$
	create procedure sp_ListarTipoProducto ()
    begin
		select * from TipoProducto;
    end $$
Delimiter ;

call sp_ListarTipoProducto();

-- ------------------------------------------Procedimiento Buscar Tipo Producto -------------------------------------------
Delimiter $$
	create procedure sp_buscarTipoProducto (in spCodigoTipoProducto int )
    begin
		select * from  TipoProducto where TipoProducto.codigoTipoProducto = spCodigoTipoProducto;
    end $$
Delimiter ;

call sp_buscarTipoProducto(1);

-- ------------------------------------------Procedimiento Actualizar Tipo Producto -------------------------------------------
Delimiter $$
	create procedure  sp_actualizarTipoProducto (in spCodigoTipoProducto int, in spDescripcion varchar (45) )
        begin 
			update TipoProducto
            set
				descripcion = spDescripcion
            where codigoTipoProducto = spCodigoTipoProducto;
		end $$
Delimiter ;

call sp_actualizarTipoProducto(3,"hola");
-- ------------------------------------------Procedimiento Eliminar Tipo Producto -------------------------------------------
Delimiter $$
	create procedure sp_eliminarTipoProducto (in spCodigoTipoProducto int)
		begin 
			delete from TipoProducto where TipoProducto.codigoTipoProducto = spCodigoTipoProducto;
		end $$
Delimiter ;




-- ------------------------------------------Entidad Compras -----------------------------------------------------
-- -----------------------------------Procedimiento Agregar Compras -------------------------------------------
Delimiter $$
	create procedure sp_agregarCompras(in spFechaDocumento date, in spDescripcion varchar(60), 
    in spTotalDocumento decimal(10,2))
		begin 
			insert into Compras(fechaDocumento, descripcion, totalDocumento)
            values (spFechaDocumento, spDescripcion, spTotalDocumento);
		end $$
Delimiter ;

call sp_agregarCompras("2020-12-10","Legos", 12.00);


-- ------------------------------------------View Listar Compras -------------------------------------------
Delimiter $$
	create procedure sp_ListarCompras ()
    begin
		select * from Compras ;
    end $$
Delimiter ;

call sp_ListarCompras();

-- -----------------------------------Procedimiento Buscar Compras -------------------------------------------
Delimiter $$
	create procedure sp_buscarCompras(in spNumeroDocumento int )
    begin
		select * from Compras where Compras.numeroDocumento = spNumeroDocumento;
    end $$
Delimiter ;

call sp_buscarCompras(1);

-- -----------------------------------Procedimiento Actualizar Compras-------------------------------------------
Delimiter $$
	create procedure  sp_actualizarCompras (in spNumeroDocumento int, in spFechaDocumento date, in spDescripcion varchar(60), 
    in spTotalDocumento decimal(10,2) )
        begin 
			update Compras
            set
				fechaDocumento = spFechaDocumento,
                descripcion = spDescripcion,
                totalDocumento = spTotalDocumento
            where numeroDocumento = spNumeroDocumento;
		end $$
Delimiter ;

-- ------------------------------------------Procedimiento Eliminar Compras -------------------------------------------
Delimiter $$
	create procedure sp_eliminarCompras (in spNumeroDocumento int)
		begin 
			delete from Compras where Compras.numeroDocumento = spNumeroDocumento;
		end $$
Delimiter ;




-- ------------------------------------------Entidad Proveedores -----------------------------------------------------
-- -----------------------------------Procedimiento Agregar Proveedores -------------------------------------------
Delimiter $$
	create procedure sp_agregarProveedores(in spNITProveedor varchar(10), in spNombresProveedor  varchar(60), 
    spApellidosProveedor varchar(60), in spDireccionProveedor varchar(150), in spRazonSocial varchar(60), in spContactoPrincipal varchar(100),
    spPaginaWeb varchar(50))
		begin 
			insert into Proveedores(NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
            values (spNITProveedor, spNombresProveedor, spApellidosProveedor, spDireccionProveedor, spRazonSocial, spContactoPrincipal, spPaginaWeb);
		end $$
Delimiter ;

call sp_agregarProveedores("123412sK2","Jose Mario", "Larios Cante", "Zona 1", "Todo Publico","5025 4241", "kinal.com" );


-- ------------------------------------------View Listar Proveedores -------------------------------------------
Delimiter $$
	create procedure sp_listarProveedores()
		begin
			select * from Proveedores ;
        end$$
Delimiter ;

call sp_listarProveedores();

-- ------------------------------------------Procedimiento Actualizar Proveedores -------------------------------------------
Delimiter $$
	create procedure  sp_actualizarProveedores (in spCodigoProveedor int, in spNITProveedor varchar(10), in spNombresProveedor  varchar(60), 
    spApellidosProveedor varchar(60), in spDireccionProveedor varchar(150), in spRazonSocial varchar(60), in spContactoPrincipal varchar(100),
    spPaginaWeb varchar(50))
        begin 
			update Proveedores
            set
				NITProveedor = spNITProveedor, 
                nombresProveedor = spNombresProveedor,
                apellidosProveedor = spApellidosProveedor,
                direccionProveedor = spDireccionProveedor,
                razonSocial = spRazonSocial,
                contactoPrincipal = spContactoPrincipal,
                paginaWeb = spPaginaWeb
            where codigoProveedor = spCodigoProveedor;
		end $$
Delimiter ;




-- ------------------------------------------ 	Eliminar Proveedores -------------------------------------------


Delimiter $$
	create procedure sp_eliminarProveedores(in spCodigoProveedores int)
		begin 
			delete from Proveedores where Proveedores.codigoProveedor = spCodigoProveedores;
		end $$
Delimiter ;







-- ------------------------------------------------Entidad Cliente -----------------------------------------------------

-- -----------------------------------------Procedimiento Agregar Cliente ------------------------------------------
Delimiter $$
	create procedure sp_agregarCliente (in spNITCliente varchar(10), in spNombresCliente varchar(50), in spApellidosCliente varchar(50), in spDireccionCliente varchar(150),
		in spTelefonoCliente varchar(45), in spCorreoCliente varchar(45))
		begin 
			insert into Clientes(NITCliente, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente)
            values (spNITCliente, spNombresCliente, spApellidosCliente, spDireccionCliente, spTelefonoCliente, spCorreoCliente);
		end $$
Delimiter ;

call sp_agregarCliente("1209DF123","Ramiro Jose","Morales López"," 9av 13-70 zona 3","5327 6129","rmorales215327@gmail.com");


-- ------------------------------------------Procedimiento Listar Cliente -------------------------------------------
Delimiter $$
	create procedure sp_ListarCliente ()
    begin
		select * from Clientes ;
    end $$
Delimiter ;

call sp_ListarCliente();


-- ------------------------------------------Procedimiento Buscar Cliente -------------------------------------------
Delimiter $$
	create procedure sp_buscarCliente (in spCodigoCliente int )
    begin
		select * from  Cliente where Cliente.codigoCliente = spCodigoCliente;
    end $$
Delimiter ;


-- ------------------------------------------Procedimiento Actualizar Cliente -------------------------------------------
Delimiter $$
	create procedure  sp_actualizarCliente (in spCodigoCliente int, in spNITCliente varchar(10), in spNombresCliente varchar(50), in spApellidosCliente varchar(50), in spDireccionCliente varchar(150),
		in spTelefonoCliente varchar(45), in spCorreoCliente varchar(45))
        begin 
			update Clientes
            set
				NITCliente = spNITCliente, 
                nombresCliente = spNombresCliente,
                apellidosCliente = spApellidosCliente,
                direccionCliente = spDireccionCliente,
                telefonoCliente = spTelefonoCliente,
                correoCliente = spCorreoCliente
            where codigoCliente = spCodigoCliente;
		end $$
Delimiter ;


-- ------------------------------------------Procedimiento Eliminar Cliente -------------------------------------------
Delimiter $$
	create procedure sp_eliminarCliente (in spCodigoCliente int)
		begin 
			delete from Clientes where Clientes.codigoCliente = spCodigoCliente;
		end $$
Delimiter ;



-- -----------------------------------------Procedimiento Agregar Producto -------------------------------------------------
Delimiter $$
create procedure sp_agregarProductos(in spCodigoProducto varchar(15),in spDescripcionProducto varchar(45), in spPrecioUnitario decimal(10,2), in spPrecioDocena decimal(10,2), in spPrecioMayor decimal(10,2), in spImagenProducto varchar(45), in spExistencia int(11), in spCodigoTipoProducto int(11), in spCodigoProveedor int)
begin
	insert into Productos(codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,imagenProducto,existencia,codigoTipoProducto,codigoProveedor)
    values (spCodigoProducto,spDescripcionProducto,spPrecioUnitario,spPrecioDocena,spPrecioMayor,spImagenProducto,spExistencia,spCodigoTipoProducto,spCodigoProveedor);
end $$
Delimiter ;
 
call sp_agregarProductos('ewda','Alta Calidad',12.00,13.00,54.00,'PNG',11,1,1);
-- call sp_agregarProductos('jjjj','Alta Calidad',12.00,13.00,54.00,'PNG',11,1,1);
 
delimiter $$
create procedure sp_listarProductos()
begin 
	select * from Productos; 
end $$
delimiter ;
 
call sp_listarProductos();
 
delimiter $$
create procedure sp_buscarProductos(in codigoProducto varchar(15))
begin
	select * from Productos where productos.codigoProducto = codigoProducto;
end $$
delimiter ;
 
call sp_buscarProductos('ewda');
 
delimiter $$
create procedure sp_actualizarProductos(in codigoProducto varchar(15),in descripcionProducto varchar(45), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), in imagenProducto varchar(45), in existencia int(11), in codigoTipoProducto int(11), in codigoProveedor int)
begin
	update Productos
    set
		productos.descripcionProducto = descripcionProducto,
		productos.precioUnitario = precioUnitario,
        productos.precioDocena = precioDocena,
        productos.precioMayor = precioMayor,
        productos.imagenProducto = imagenProducto,
        productos.existencia = existencia,
        productos.codigoTipoProducto = codigoTipoProducto,
        productos.codigoProveedor = codigoProveedor
	where
		productos.codigoProducto = codigoProducto;
end $$
delimiter ;
 
call sp_actualizarProductos('ewda','Alta',14.00,15.00,50.00,'JPG',10,1,1);
 
delimiter $$
create procedure sp_eliminarProductos(in codigoProducto varchar(45))
begin 
	delete from Productos where productos.codigoProducto = codigoProducto;
end $$
delimiter ;
 
call sp_eliminarProductos('ewda');




