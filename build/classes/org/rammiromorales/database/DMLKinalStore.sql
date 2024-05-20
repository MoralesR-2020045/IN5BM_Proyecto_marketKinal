-- DML Ramiro Donovan Morales López 
-- 2020045
-- Fecha de Creacion: 23/04/2024

use DBKinalStore;
set global time_zone = "-6:00";+

-- ------------------------------------------Entidad Cantidad Producto Proveedor -----------------------------------------------------
-- -----------------------------------Procedimiento Agregar Cantidad Producto Proveedor -------------------------------------------
Delimiter $$
	create procedure sp_agregarCantidadDeProdutoProveedor( in spCantidadProductoProveedor varchar(45))
		begin 
			insert into CantidadDeProdutoProveedor(cantidadProductoProveedor)
            values (spcantidadProductoProveedor );
		end $$
Delimiter ;

call sp_agregarCantidadDeProdutoProveedor("1 caja");

-- ------------------------------------------Procedimiento Listar Cantidad Producto Proveedor-------------------------------------------
Delimiter $$
	create procedure sp_listarCantidadDeProdutoProveedor ()
    begin
		select * from CantidadDeProdutoProveedor ;
    end $$
Delimiter ;

call sp_listarCantidadDeProdutoProveedor();

-- ------------------------------------------Procedimiento Eliminar Cantidad Producto Proveedor -------------------------------------------
Delimiter $$
	create procedure sp_eliminarCantidadDeProdutoProveedor (in spCodigoCargoEmpleado int)
		begin 
			delete from CantidadDeProdutoProveedor where 
            CantidadDeProdutoProveedor.codigoCantidadProveedor = spCodigoCargoEmpleado;
		end $$
Delimiter ;

-- ------------------------------------------Procedimiento Actualizar Cantidad Producto Proveedor -------------------------------------------
Delimiter $$
	create procedure  sp_actualizarCantidadDeProdutoProveedor (in spCodigoCargoEmpleado int, in spCantidadProductoProveedor varchar (45))
        begin 
			update CantidadDeProdutoProveedor
            set
                cantidadProductoProveedor = spCantidadProductoProveedor
            where codigoCantidadProveedor = spCodigoCargoEmpleado;
		end $$
Delimiter ;

-- call sp_actualizarCantidadDeProdutoProveedor();

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

Delimiter $$
	create procedure sp_buscarProveedores(in spcodigoProveedor int)
		begin
			select * from Proveedores where Proveedores.codigoProveedor = spcodigoProveedor;
        end$$
Delimiter ;

call sp_buscarProveedores(1);

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
 
-- call sp_eliminarProductos('ewda');
-- ---------------------------  Empleados --------------------------- 

delimiter $$
create procedure sp_agregarEmpleados(in codigoEmpleado int, in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10,2), in direccion varchar(150), in turno varchar(15), in codigoCargoEmpleado int)
begin 
	insert into Empleados(codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
    values (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado);
end $$
delimiter ;

call sp_agregarEmpleados(1,'Pedro','Gomez','10.0','10 Calle y 10 Avenida','M',1);


delimiter $$
create procedure sp_listarEmpleados()
begin 
	select * from Empleados;
end $$
delimiter ;

call sp_listarEmpleados();

delimiter $$
create procedure sp_buscarEmpleados(in codigoEmpleado int)
begin 
	select * from Empleados where Empleados.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

call sp_buscarEmpleados(1);

delimiter $$
create procedure sp_actualizarEmpleados(in codigoEmpleado int, in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10,2), in direccion varchar(150), in turno varchar(15), in codigoCargoEmpleado int)
begin 
	update Empleados
    set	
		Empleados.nombresEmpleado = nombresEmpleado,
		Empleados.apellidosEmpleado = apellidosEmpleado,
        Empleados.sueldo = sueldo,
        Empleados.direccion = direccion,
        Empleados.turno = Empleados.turno,
        Empleados.codigoCargoEmpleado = codigoCargoEmpleado
	where
		Empleados.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

call sp_actualizarEmpleados(1,'David','G',23.0,'29 calle','M',1);

delimiter $$
create procedure sp_eliminarEmpleados(in codigoEmpleado int)
begin 
	delete from Empleados where Empleados.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;


-- ---------------------------  Email Proveedor --------------------------- 

delimiter $$
create procedure sp_agregarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)
begin
	insert into EmailProveedor(codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor)
    values(codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor);
end $$
delimiter ;

call sp_agregarEmailProveedor(1,'dtubac@kinal','Guatemala',1);

delimiter $$
create procedure sp_listarEmailProveedor()
begin
	select * from EmailProveedor;
end $$
delimiter ;

call sp_listarEmailProveedor;

delimiter $$
create procedure sp_buscarEmailProveedor(in codigoEmailProveedor int)
begin
	select*from EmailProveedor where EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

call sp_buscarEmailProveedor(1);

delimiter $$
create procedure sp_actualizarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)
begin
	update EmailProveedor
	set
		EmailProveedor.emailProveedor = emailProveedor,
        EmailProveedor.descripcion = descripcion,
        EmailProveedor.codigoProveedor = codigoProveedor
	where
		EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarEmailProveedor(in codigoEmailProveedor int)
begin
	delete from EmailProveedor where EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

-- --------------------------- Telefono Proveedor --------------------------- 

delimiter $$
create procedure sp_agregarTelefonoProveedor(in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
begin
	insert into TelefonoProveedor(codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
    values (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor);
end $$
delimiter ;

call sp_agregarTelefonoProveedor(1,'12345678','87654321','502',1);

delimiter $$
create procedure sp_listarTelefonoProveedor()
begin
	select * from TelefonoProveedor;
end $$
delimiter ;

call sp_listarTelefonoProveedor;

delimiter $$
create procedure sp_buscarTelefonoProveedor(in codigoTelefonoProveedor int)
begin
	select * from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;

call sp_buscarTelefonoProveedor(1);

delimiter $$
create procedure sp_actualizarTelefonoProveedor(in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
begin
	update TelefonoProveedor
    set
		TelefonoProveedor.numeroPrincipal = numeroPrincipal,
        TelefonoProveedor.numeroSecundario = numeroSecundario, 
        TelefonoProveedor.observaciones = observaciones,
        TelefonoProveedor.codigoProveedor = codigoProveedor
	where 
		TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarTelefonoProveedor(in codigoTelefonoProveedor int)
begin
	delete from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;



-- --------------------------- Detalle Compra --------------------------- 

delimiter $$
create procedure sp_agregarDetalleCompra(in codigoDetalleCompra int, in costoUnitario decimal(10,2), in cantidad int, in codigoProducto varchar(15), in numeroDocumento int)
begin
	insert into DetalleCompra(codigoDetalleCompra, costoUnitario, cantidad, codigoProducto,numeroDocumento)
    values (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento);
end $$
delimiter ;

call sp_agregarDetalleCompra(4,10.5,5,"ewda",1);

delimiter $$
create procedure sp_listarDetalleCompra()
begin
	select * from DetalleCompra;
end $$
delimiter ;

call sp_listarDetalleCompra();

delimiter $$
create procedure sp_buscarDetalleCompra(in codigoDetalleCompra int)
begin
	select * from DetalleCompra where DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarDetalleCompra(in codigoDetalleCompra int, in costoUnitario decimal(10,2), in cantidad int, in codigoProducto varchar(15), in numeroDocumento int)
begin
	update DetalleCompra
    set
		DetalleCompra.costoUnitario = costoUnitario,
        DetalleCompra.cantidad = cantidad,
        DetalleCompra.codigoProducto = codigoProducto,
        DetalleCompra.numeroDocumento = numeroDocumento
	where
		DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarDetalleCompra(in codigoDetalleCompra int)
begin
	delete from DetalleCompra where DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;

-- ---------------------------  Factura --------------------------- 

delimiter $$
create procedure sp_agregarFactura(in numeroFactura int, in estado varchar(50), in totalFactura decimal(10,2), in fechaFactura date,in codigoCliente int, in codigoEmpleado int)
begin
	insert into Factura(numeroFactura,estado,totalFactura,fechaFactura,codigoCliente,codigoEmpleado)
    values (numeroFactura,estado,totalFactura,fechaFactura,codigoCliente,codigoEmpleado);
end $$
delimiter ;

call sp_agregarFactura(1,'Positivo',34.0,'2022-06-06',1,1);

delimiter $$
create procedure sp_listarFactura()
begin
	select * from Factura;
end $$
delimiter ;

delimiter $$
create procedure sp_buscarFactura(in numeroFactura int)
begin
	select * from Factura where Factura.numeroFactura = numeroFactura;
end $$
delimiter ;

call sp_buscarFactura(1);

delimiter $$
create procedure sp_actualizarFactura(in numeroFactura int, in estado varchar(50), in totalFactura decimal(10,2), in fechaFactura date,in codigoCliente int, in codigoEmpleado int)
begin
	update Factura
    set
		Factura.estado = estado,
        Factura.totalFactura = totalFactura,
        Factura.fechaFactura = fechaFactura,
        Factura.codigoCliente  = codigoCliente,
        Factura.codigoEmpleado = codigoEmpleado
	where
		Factura.numeroFactura = numeroFactura;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarFactura(in numeroFactura int)
begin
	delete from Factura where Factura.numeroFactura = numeroFactura;
end $$
delimiter ;

-- ---------------------------  Detalle Factura --------------------------- 

delimiter $$
create procedure sp_agregarDetalleFactura(in codigoDetalleFactura int, in precioUnitario decimal(10,2), in cantidad int, in numeroFactura int, in codigoProducto varchar(15))
begin
	insert into DetalleFactura(codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto)
    values (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto);
end $$
delimiter ;

call sp_agregarDetalleFactura(1,0.00,3,1,'ewda');
call sp_agregarDetalleFactura(2,2.00,3,1,'ewda');

delimiter $$
create procedure sp_listarDetallesFacturas()
begin
	select * from DetalleFactura;
end $$
delimiter ;

call sp_listarDetallesFacturas();

delimiter $$
create procedure sp_buscarDetalleFactura(in codigoDetalleFactura int)
begin 
	select * from DetalleFactura where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;


delimiter $$
create procedure sp_actualizarDetalleFactura(in codigoDetalleFactura int, in precioUnitario decimal(10,2), in cantidad int, in numeroFactura int, in codigoProducto varchar(15))
begin
	update DetalleFactura
		set
			DetalleFactura.precioUnitario = precioUnitario,
            DetalleFactura.cantidad = cantidad,
            DetalleFactura.numeroFactura = numeroFactura,
            DetalleFactura.codigoProducto = codigoProducto
		where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarDetalleFactura(in codigoDetalleFactura int)
begin
	delete from DetalleFactura where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;



delimiter $$
create trigger tr_PrecioProductos_After_Insert
after insert on DetalleCompra
for each row 
begin
	declare total decimal(10,2);
    declare cantidad int;
    
    set total = new.costoUnitario * new.cantidad;

	update Productos
	set precioUnitario = total * 0.40,
		precioDocena  = total * 0.35 * 12,
        precioMayor = total * 0.25
    where Productos.codigoProducto = new.codigoProducto;
    
	update Productos
        set Productos.existencia = Productos.existencia - new.cantidad
	where Productos.codigoProducto = new.codigoProducto;

end $$
delimiter ;



call sp_listarProductos;

call sp_listarDetalleCompra;

call sp_listarCompras();

delimiter $$
create trigger tr_TotalDocumento_After_Insert
after insert on DetalleCompra
for each row
begin
    declare total decimal(10,2);
    
    select sum(costoUnitario * cantidad) into total from DetalleCompra 
    where numeroDocumento = NEW.numeroDocumento;
    
    update Compras 
		set totalDocumento = total 
	where numeroDocumento = NEW.numeroDocumento;
end $$
delimiter ;

select * from DetalleFactura;

delimiter $$
create trigger tr_PrecioUnitario_After_Insert
after insert on DetalleCompra
for each row
begin

	declare precioP decimal(10,2);
    
    set precioP = (select precioUnitario from Productos where codigoProducto = new.codigoProducto);
    
    update DetalleFactura
    set precioUnitario = precioP
    where codigoProducto = NEW.codigoProducto;
end $$
delimiter ;

delimiter $$
create trigger tr_TotalFactura_After_Insert
after insert on DetalleFactura
for each row
begin
	declare totalFactura decimal(10,2);
    
    select sum(precioUnitario * cantidad) into totalFactura from DetalleFactura
    where numeroFactura = new.numeroFactura;
    
    update Factura
		set totalFactura = totalFactura
	where numeroFactura = new.numeroFactura;
end $$
delimiter ;

-- call sp_agregarProductos('kinals','Alta Calidad',12.00,13.00,54.00,'PNG',11,1,1);
-- call sp_agregarCompras("2020-12-10","Legla", 12.00);
-- call sp_agregarDetalleCompra(4,5,2,"kinals",1);
