-- DML Ramiro Donovan Morales López 
-- 2020045
-- Fecha de Creacion: 23/04/2024

use DBKinalStore;
set global time_zone = "-6:00";+


-- ------------------------------------------Entidad Producto Proveedor -----------------------------------------------------
Delimiter $$
	create procedure sp_listarProductoProveedor ()
		begin
			select * from ProductoProveedor ;
		end $$
Delimiter ;

call sp_listarProductoProveedor();

Delimiter $$
	create procedure sp_buscarProductoProveedor (in idProductoProveedor int)
		begin
			select * from ProductoProveedor where ProductoProveedor.idProductoProveedor = idProductoProveedor; 
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_eliminarProductoProveedor (in idProductoProveedor int)
		begin 
			delete from ProductoProveedor where 
            ProductoProveedor.idProductoProveedor = idProductoProveedor;
		end $$
Delimiter ;

-- ------------------------------------------Entidad Cargo Empleado -----------------------------------------------------
Delimiter $$
	create procedure sp_agregarCargoEmpleado(in spNombreCargo varchar (45), in spDescripcionCargo varchar(45))
		begin 
			insert into CargoEmpleado(nombreCargo, descripcionCargo)
            values (spNombreCargo, spDescripcionCargo);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarCargoEmpleado ()
    begin
		select * from CargoEmpleado ;
    end $$
Delimiter ;

Delimiter $$
	create procedure sp_BuscarCargoEmpleado (in spCodigoCargoEmpleado int)
		begin
			select * from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = spCodigoCargoEmpleado; 
		end $$
Delimiter ;

call sp_BuscarCargoEmpleado(1);

Delimiter $$
	create procedure sp_eliminarCargoEmpleado (in spCodigoCargoEmpleado int)
		begin 
			delete from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = spCodigoCargoEmpleado;
		end $$
Delimiter ;

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


-- ------------------------------------------Entidad TIPO PRODUCTO -----------------------------------------------------
Delimiter $$
	create procedure sp_agregarTipoProducto(in spDescripcion varchar (45))
		begin 
			insert into TipoProducto(descripcion)
            values (spDescripcion);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarTipoProducto ()
    begin
		select * from TipoProducto;
    end $$
Delimiter ;

Delimiter $$
	create procedure sp_buscarTipoProducto (in spCodigoTipoProducto int )
    begin
		select * from  TipoProducto where TipoProducto.codigoTipoProducto = spCodigoTipoProducto;
    end $$
Delimiter ;

Delimiter $$
	create procedure  sp_actualizarTipoProducto (in spCodigoTipoProducto int, in spDescripcion varchar (45) )
        begin 
			update TipoProducto
            set
				descripcion = spDescripcion
            where codigoTipoProducto = spCodigoTipoProducto;
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_eliminarTipoProducto (in spCodigoTipoProducto int)
		begin 
			delete from TipoProducto where TipoProducto.codigoTipoProducto = spCodigoTipoProducto;
		end $$
Delimiter ;



-- ------------------------------------------Entidad Compras -----------------------------------------------------
Delimiter $$
	create procedure sp_agregarCompras(in spFechaDocumento date, in spDescripcion varchar(60), 
    in spTotalDocumento decimal(10,2))
		begin 
			insert into Compras(fechaDocumento, descripcion, totalDocumento)
            values (spFechaDocumento, spDescripcion, spTotalDocumento);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarCompras ()
		begin
			select * from Compras ;
		end $$
Delimiter ;

Delimiter $$ 
	create procedure sp_buscarCompras(in spNumeroDocumento int )
		begin
			select * from Compras where Compras.numeroDocumento = spNumeroDocumento;
		end $$
Delimiter ;

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

Delimiter $$
	create procedure sp_eliminarCompras (in spNumeroDocumento int)
		begin 
			delete from Compras where Compras.numeroDocumento = spNumeroDocumento;
		end $$
Delimiter ;



-- ------------------------------------------Entidad Proveedores -----------------------------------------------------
Delimiter $$
	create procedure sp_agregarProveedores(in spNITProveedor varchar(10), in spNombresProveedor  varchar(60), 
    spApellidosProveedor varchar(60), in spDireccionProveedor varchar(150), in spRazonSocial varchar(60), in spContactoPrincipal varchar(100),
    spPaginaWeb varchar(50))
		begin 
			insert into Proveedores(NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
            values (spNITProveedor, spNombresProveedor, spApellidosProveedor, spDireccionProveedor, spRazonSocial, spContactoPrincipal, spPaginaWeb);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_listarProveedores()
		begin
			select * from Proveedores ;
        end$$
Delimiter ;

Delimiter $$
	create procedure sp_buscarProveedores(in spcodigoProveedor int)
		begin
			select * from Proveedores where Proveedores.codigoProveedor = spcodigoProveedor;
        end$$
Delimiter ;

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

Delimiter $$
	create procedure sp_eliminarProveedores(in spCodigoProveedores int)
		begin 
			delete from Proveedores where Proveedores.codigoProveedor = spCodigoProveedores;
		end $$
Delimiter ;



-- ------------------------------------------------Entidad Cliente -----------------------------------------------------

Delimiter $$
	create procedure sp_agregarCliente (in spNITCliente varchar(10), in spNombresCliente varchar(50), in spApellidosCliente varchar(50), in spDireccionCliente varchar(150),
		in spTelefonoCliente varchar(45), in spCorreoCliente varchar(45))
		begin 
			insert into Clientes(NITCliente, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente)
            values (spNITCliente, spNombresCliente, spApellidosCliente, spDireccionCliente, spTelefonoCliente, spCorreoCliente);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarCliente ()
    begin
		select * from Clientes ;
    end $$
Delimiter ;

Delimiter $$
	create procedure sp_buscarCliente (in spCodigoCliente int )
    begin
		select * from  Clientes where Clientes.codigoCliente = spCodigoCliente;
    end $$
Delimiter ;

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

Delimiter $$
	create procedure sp_eliminarCliente (in spCodigoCliente int)
		begin 
			delete from Clientes where Clientes.codigoCliente = spCodigoCliente;
		end $$
Delimiter ;



-- ------------------------------------------------Entidad Producto -----------------------------------------------------

Delimiter $$
	create procedure sp_listarProductos()
		begin 
			select * from Productos; 
		end $$
Delimiter ;
 
CREATE VIEW vw_Producto AS SELECT ProductoProveedor.nombreProductoProveedor, 
Productos.descripcionProducto  FROM Productos 
INNER JOIN ProductoProveedor ON Productos.idProductoProveedor = ProductoProveedor.idProductoProveedor;

Delimiter $$
	create procedure sp_buscarProductos(in codigoProducto varchar(15))
		begin
			select * from Productos where productos.codigoProducto = codigoProducto;
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_eliminarProductos(in codigoProducto varchar(45))
		begin 
			delete from Productos where productos.codigoProducto = codigoProducto;
		end $$
Delimiter ;



-- ---------------------------  Empleados --------------------------- 
Delimiter $$
	create procedure sp_agregarEmpleados(in codigoEmpleado int, in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10,2), in direccion varchar(150), in turno varchar(15), in codigoCargoEmpleado int)
		begin 
			insert into Empleados(codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
			values (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_listarEmpleados()
		begin 
			select * from Empleados;
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_buscarEmpleados(in codigoEmpleado int)
		begin 
			select * from Empleados where Empleados.codigoEmpleado = codigoEmpleado;
		end $$
Delimiter ;

Delimiter $$
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
Delimiter ;

Delimiter $$
create procedure sp_eliminarEmpleados(in codigoEmpleado int)
begin 
	delete from Empleados where Empleados.codigoEmpleado = codigoEmpleado;
end $$
Delimiter ;

-- ---------------------------  Email Proveedor --------------------------- 

Delimiter $$
	create procedure sp_agregarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)
		begin
			insert into EmailProveedor(codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor)
			values(codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_listarEmailProveedor()
		begin
			select * from EmailProveedor;
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_buscarEmailProveedor(in codigoEmailProveedor int)
		begin
			select*from EmailProveedor where EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
		end $$
Delimiter ;

Delimiter $$
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
Delimiter ;

Delimiter $$
	create procedure sp_eliminarEmailProveedor(in codigoEmailProveedor int)
		begin
			delete from EmailProveedor where EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
		end $$
Delimiter ;



-- --------------------------- Telefono Proveedor --------------------------- 

Delimiter $$
	create procedure sp_agregarTelefonoProveedor(in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
		begin
			insert into TelefonoProveedor(codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
			values (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_listarTelefonoProveedor()
		begin
			select * from TelefonoProveedor;
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_buscarTelefonoProveedor(in codigoTelefonoProveedor int)
		begin
			select * from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
		end $$
Delimiter ;

Delimiter $$
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
Delimiter ;

Delimiter $$
	create procedure sp_eliminarTelefonoProveedor(in codigoTelefonoProveedor int)
		begin
			delete from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
		end $$
Delimiter ;



-- --------------------------- Detalle Compra --------------------------- 
Delimiter $$
	create procedure sp_listarDetalleCompra()
		begin
			select * from DetalleCompra;
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_buscarDetalleCompra(in codigoDetalleCompra int)
		begin
			select * from DetalleCompra where DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_eliminarDetalleCompra(in codigoDetalleCompra int)
		begin
			delete from DetalleCompra where DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
		end $$
Delimiter ;



-- ---------------------------  Factura --------------------------- 

Delimiter $$
	create procedure sp_agregarFactura(in numeroFactura int, in estado varchar(50), in totalFactura decimal(10,2), in fechaFactura date,in codigoCliente int, in codigoEmpleado int)
		begin
			insert into Factura(numeroFactura,estado,totalFactura,fechaFactura,codigoCliente,codigoEmpleado)
			values (numeroFactura,estado,totalFactura,fechaFactura,codigoCliente,codigoEmpleado);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_listarFactura()
		begin
			select * from Factura;
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_buscarFactura(in numeroFactura int)
		begin
			select * from Factura where Factura.numeroFactura = numeroFactura;
		end $$
Delimiter ;

Delimiter $$
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
Delimiter ;

Delimiter $$
	create procedure sp_eliminarFactura(in numeroFactura int)
		begin
			delete from Factura where Factura.numeroFactura = numeroFactura;
		end $$
Delimiter ;



-- ---------------------------  Detalle Factura --------------------------- 
Delimiter $$
	create procedure sp_listarDetallesFacturas()
		begin
			select * from DetalleFactura;
		end $$
Delimiter ;

call sp_listarDetallesFacturas();

Delimiter $$
	create procedure sp_buscarDetalleFactura(in codigoDetalleFactura int)
		begin 
			select * from DetalleFactura where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
		end $$
Delimiter ;



-- -------------------------------------Triggers o Procesos de Automatizacion -----------------------------------
-- CRUD DE INVENTARIO O PRODUCTOS DE PROVEEDORES 

Delimiter $$
	create procedure sp_agregarProductoProveedor(in nombreProductoProveedor varchar(60), in descripcionProducto varchar(100),
    in precioProveedor decimal(10,2), in cantidadDeProducto int, in existenciaPorDescripcion int, 
    in existenciaTotalDelProducto int)
		begin 
			declare total int;
            set total = cantidadDeProducto * existenciaPorDescripcion;
            
			insert into ProductoProveedor(nombreProductoProveedor, descripcionProducto, precioProveedor, 
            cantidadDeProducto, existenciaPorDescripcion, existenciaTotalDelProducto)
            
            values (nombreProductoProveedor, descripcionProducto, precioProveedor, cantidadDeProducto, 
            existenciaPorDescripcion, total);
		end $$
Delimiter ;

Delimiter $$
	create procedure  sp_actualizarProductoProveedor (in idProductoProveedor int, in nombreProductoProveedor varchar(60), 
    in descripcionProducto varchar(100), in precioProveedor decimal(10,2), in cantidadDeProducto int, 
    in existenciaPorDescripcion int, in existenciaTotalDelProducto int)
        begin 
			declare total int;
            set total =  actualizar(idProductoProveedor, cantidadDeProducto, existenciaPorDescripcion);
			update ProductoProveedor
				set
					ProductoProveedor.nombreProductoProveedor = nombreProductoProveedor,
                    ProductoProveedor.descripcionProducto = descripcionProducto,
					ProductoProveedor.precioProveedor = precioProveedor,
                    ProductoProveedor.cantidadDeProducto = cantidadDeProducto,
                    ProductoProveedor.existenciaPorDescripcion = existenciaPorDescripcion,
                    ProductoProveedor.existenciaTotalDelProducto = total
				where ProductoProveedor.idProductoProveedor = idProductoProveedor;
		end $$
Delimiter ;

Delimiter $$
	create function actualizar ( idProductoProveedor int, cantidadDeProductoUpdate int,  existenciaPorDescripcionUpdate int)
    returns int
     deterministic
		begin
        declare cantidadUnidades int ;
            declare unidadesDescripcion int;
            declare resultado int;
            declare total int; 
            declare sumatoria int;

			select cantidadDeProducto into cantidadUnidades from ProductoProveedor where idProductoProveedor = idProductoProveedor limit 1;
            select  existenciaPorDescripcion into unidadesDescripcion from ProductoProveedor where idProductoProveedor = idProductoProveedor limit 1;
			select existenciaTotalDelProducto into total from ProductoProveedor where idProductoProveedor = idProductoProveedor limit 1;

            case 
				when cantidadUnidades = cantidadDeProductoUpdate and unidadesDescripcion = existenciaPorDescripcionUpdate then 
					return total;
				else 
					set resultado = cantidadDeProductoUpdate *existenciaPorDescripcionUpdate;
					set sumatoria = total + resultado;
					return sumatoria;
            end case;
		end $$
Delimiter ;

Delimiter $$
	create trigger trProductoProveedorAfterUpdate 
		after update on ProductoProveedor 
		for each row 
			begin 
				declare precio decimal(10,2);
                declare unidadesProducto int;
				declare existencias int;
                declare almacenador decimal(10,2);
                
				select precioProveedor into precio from ProductoProveedor where idProductoProveedor = new.idProductoProveedor;
				select cantidadDeProducto into unidadesProducto from ProductoProveedor where idProductoProveedor = new.idProductoProveedor;
                select existenciaTotalDelProducto into existencias from ProductoProveedor where idProductoProveedor = new.idProductoProveedor;
				set almacenador =  precio/unidadesProducto;
                
                update Productos
                set precioUnitario = almacenador * 0.40,
                 precioDocena = almacenador * 0.35*12,
                 precioMayor = almacenador * 0.25*24,
                 existencia = existencias
                where idProductoProveedor = new.idProductoProveedor;
            end $$ 
Delimiter ;

--  -------------------------------------------------- CRUD DE PRODUCTO --------------------------------------------------------------------

Delimiter $$
create procedure sp_agregarProductos(in spCodigoProducto varchar(15), in spDescripcionProducto varchar(45), 
in spPrecioUnitario decimal(10,2), in spPrecioDocena decimal(10,2), in spPrecioMayor decimal(10,2), 
in spImagenProducto varchar(45), in spExistencia int(11), in  spIdProductoProveedor int,
 in spCodigoTipoProducto int(11), in spCodigoProveedor int)
	begin
		declare total int;
        select existenciaTotalDelProducto into total from ProductoProveedor where idProductoProveedor = spIdProductoProveedor ;
		
        insert into Productos(codigoProducto, descripcionProducto,precioUnitario, precioDocena,precioMayor, 
        imagenProducto, existencia, idProductoProveedor, codigoTipoProducto, codigoProveedor)
		values (spCodigoProducto,spDescripcionProducto, spPrecioUnitario,spPrecioDocena, spPrecioMayor, 
        spImagenProducto, total, spIdProductoProveedor, spCodigoTipoProducto, spCodigoProveedor);
	
    end $$
Delimiter ;


Delimiter $$
	create procedure sp_actualizarProductos(in codigoProducto varchar(15),
    in descripcionProducto varchar(45), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), 
    in precioMayor decimal(10,2), in imagenProducto varchar(45), in existencia int(11), in spIdProductoProveedor int,
    in codigoTipoProducto int(11), in codigoProveedor int)
		begin
			declare precio decimal(10,2);
			declare unidadesProducto int;
			declare existencias int;
			declare almacenador decimal(10,2);
                
			select (precioProveedor/ cantidadDeProducto)into precio from ProductoProveedor where idProductoProveedor = spIdProductoProveedor limit 1;
			select existenciaTotalDelProducto into existencias from ProductoProveedor where idProductoProveedor = spIdProductoProveedor limit 1;
                
			update Productos
			set
				productos.descripcionProducto = descripcionProducto,
				productos.precioUnitario = precio*0.40,
				productos.precioDocena = precio * 0.35*12,
				productos.precioMayor = precio * 0.25*24,
				productos.imagenProducto = imagenProducto,
				productos.existencia = existencias,
                productos.idProductoProveedor = idProductoProveedor,
				productos.codigoTipoProducto = codigoTipoProducto,
				productos.codigoProveedor = codigoProveedor
			where
			productos.codigoProducto = codigoProducto;
		end $$
Delimiter ;


-- ------------------------------------------Entidad DETALLE COMPRA -----------------------------------------------
Delimiter $$
	create procedure sp_agregarDetalleCompra(in spCodigoDetalleCompra int, in spCostoUnitario decimal(10,2), in spCantidad int, in spCodigoProducto varchar(15), in spNumeroDocumento int)
		begin
			declare total decimal(10,2);
			set total = calculoDePrecio(spCodigoProducto);
		
			insert into DetalleCompra(codigoDetalleCompra, costoUnitario, cantidad, codigoProducto,numeroDocumento)
			values (spCodigoDetalleCompra, total, spCantidad, spCodigoProducto, spNumeroDocumento);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_actualizarDetalleCompra(in codigoDetalleCompra int, in costoUnitario decimal(10,2), in cantidad int, in spCodigoProducto varchar(15), in numeroDocumento int)
		begin
			declare total decimal(10,2);
			set total = calculoDePrecio(spCodigoProducto);
			update DetalleCompra
				set
					DetalleCompra.costoUnitario = total,
					DetalleCompra.cantidad = cantidad,
					DetalleCompra.codigoProducto = codigoProducto,
					DetalleCompra.numeroDocumento = numeroDocumento
				where
					DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
		end $$
Delimiter ;

Delimiter $$
	create function calculoDePrecio (spCodigoProducto varchar(15))
    returns decimal(10,2)
     deterministic
		begin
			declare idPProveedor int ;
            declare precio decimal(10,2);
            declare cantidad decimal(10,2);
            declare total decimal(10,2);
            declare porcentaje decimal(10,2);
            declare totales decimal(10,2);
            
			select idProductoProveedor into idPProveedor from Productos where codigoProducto = spCodigoProducto  ;
            select  precioProveedor into precio from ProductoProveedor where idProductoProveedor = idPProveedor  ;
			select  cantidadDeProducto into cantidad from ProductoProveedor where idProductoProveedor = idPProveedor ;
            set total = (precio/cantidad);
            set porcentaje = total*0.4;
			set totales = total + porcentaje;
            return totales;
		end $$
Delimiter ;


Delimiter $$
	create trigger trPrecioProductosAfterInsert
		after insert on DetalleCompra
		for each row 
			begin
				declare precio decimal(10,2);
                declare totales int;
                declare idPProveedor int;
                select idProductoProveedor into idPProveedor from Productos where codigoProducto = new.codigoProducto;
				select (existencia-new.cantidad) into totales from Productos where codigoProducto = new.codigoProducto;
                select  (precioProveedor /cantidadDeProducto) into precio from ProductoProveedor where idProductoProveedor = idPProveedor;	
				
				update Productos
				set precioUnitario = precio* 0.40,
					precioDocena  = precio * 0.35 * 12,
					precioMayor = precio * 0.25 * 24
				where Productos.codigoProducto = new.codigoProducto;
                
				update Productos
					set Productos.existencia = totales
				where Productos.codigoProducto = new.codigoProducto;
                
                update ProductoProveedor
                set ProductoProveedor.existenciaTotalDelProducto = totales
                where idProductoProveedor = idPProveedor;
			end $$
Delimiter ;

Delimiter $$
	create trigger trPrecioProductosAfterUpdate
		after update on DetalleCompra
		for each row 
			begin
				declare precio decimal(10,2);
                declare totales int;
                declare idPProveedor int;
                select idProductoProveedor into idPProveedor from Productos where codigoProducto = new.codigoProducto;
				select (existencia-new.cantidad) into totales from Productos where codigoProducto = new.codigoProducto;
                select  (precioProveedor /cantidadDeProducto) into precio from ProductoProveedor where idProductoProveedor = idPProveedor;	
				
				update Productos
				set precioUnitario = precio* 0.40,
					precioDocena  = precio * 0.35 * 12,
					precioMayor = precio * 0.25 * 24
				where Productos.codigoProducto = new.codigoProducto;
                
				update Productos
					set Productos.existencia = totales
				where Productos.codigoProducto = new.codigoProducto;
                
                update ProductoProveedor
                set ProductoProveedor.existenciaTotalDelProducto = totales
                where idProductoProveedor = idPProveedor;
			end $$
Delimiter ;

Delimiter $$
	create trigger trTotalDocumentoAfterInsert
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
Delimiter ;

Delimiter $$
	create trigger trTotalDocumentoAfterUpdate
		after update on DetalleCompra
		for each row
			begin
				declare total decimal(10,2);
				
				select sum(costoUnitario * cantidad) into total from DetalleCompra 
				where numeroDocumento = NEW.numeroDocumento;
				
				update Compras 
					set totalDocumento = total 
				where numeroDocumento = NEW.numeroDocumento;
			end $$
Delimiter ;


-- -------------------------------------------CRUD DETALLE FACTURA -------------------------------------------------------
Delimiter $$
	create procedure sp_agregarDetalleFactura(in codigoDetalleFactura int, in precioUnitario decimal(10,2), in cantidad int, in numeroFactura int, in codigoProducto varchar(15))
		begin
			declare precioUnitarioCalculo decimal(10,2);
            set precioUnitarioCalculo = fn_precioUnitario(codigoProducto);
            
			insert into DetalleFactura(codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto)
			values (codigoDetalleFactura, precioUnitarioCalculo, cantidad, numeroFactura, codigoProducto);
		end $$
Delimiter ;

Delimiter $$
	create procedure sp_actualizarDetalleFactura(in codigoDetalleFactura int, in precioUnitario decimal(10,2), in cantidad int, in numeroFactura int, in codigoProducto varchar(15))
		begin
			declare precioUnitarioCalculo decimal(10,2);
            set precioUnitarioCalculo = fn_precioUnitario(codigoProducto);
			update DetalleFactura
				set
					DetalleFactura.precioUnitario = precioUnitarioCalculo,
					DetalleFactura.cantidad = cantidad,
					DetalleFactura.numeroFactura = numeroFactura,
					DetalleFactura.codigoProducto = codigoProducto
				where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
		end $$
Delimiter ;


Delimiter $$
	create function fn_precioUnitario (spCodigoProducto varchar(15))
    returns decimal(10,2)
     deterministic
		begin
			declare idPProveedor int ;
            declare precio decimal(10,2);
            declare cantidad decimal(10,2);
            declare total decimal(10,2);
            declare porcentaje decimal(10,2);
            declare totales decimal(10,2);
            
			select idProductoProveedor into idPProveedor from Productos where codigoProducto = spCodigoProducto  ;
            select  precioProveedor into precio from ProductoProveedor where idProductoProveedor = idPProveedor  ;
			select  cantidadDeProducto into cantidad from ProductoProveedor where idProductoProveedor = idPProveedor ;
            set total = (precio/cantidad);
            set porcentaje = total*0.4;
			set totales = total + porcentaje;
            return totales;
		end $$
Delimiter ;


Delimiter $$
	create trigger trFacturaAfterInsert
		after insert on DetalleFactura
		for each row
			begin
				declare total decimal(10,2);
                declare idFactura int;
                declare idPProveedor int;
                declare totalPProveedor int;
                
                select idProductoProveedor into idPProveedor from Productos where codigoProducto = new.codigoProducto;
				select existenciaTotalDelProducto into totalPProveedor from ProductoProveedor where idProductoProveedor = idPProveedor;
                select numeroFactura into idFactura from DetalleFactura where codigoDetalleFactura = new.codigoDetalleFactura;
				select sum(precioUnitario * cantidad) into total from DetalleFactura;
				
				update Factura 
					set totalFactura = total 
				where idFactura = idFactura;
                
                update ProductoProveedor
                set existenciaTotalDelProducto = (totalPProveedor - new.cantidad)
                where idProductoProveedor = idPProveedor;
			end $$
Delimiter ;



Delimiter $$
	create trigger trFacturaAfterUpdate
		after update on DetalleFactura
		for each row
			begin
				declare total decimal(10,2);
                declare idFactura int;
                declare idPProveedor int;
                declare totalPProveedor int;
                
                select idProductoProveedor into idPProveedor from Productos where codigoProducto = new.codigoProducto;
				select existenciaTotalDelProducto into totalPProveedor from ProductoProveedor where idProductoProveedor = idPProveedor;
                select numeroFactura into idFactura from DetalleFactura where codigoDetalleFactura = new.codigoDetalleFactura;
				select sum(precioUnitario * cantidad) into total from DetalleFactura;
				
				update Factura 
					set totalFactura = total 
				where idFactura = idFactura;
                
                update ProductoProveedor
                set existenciaTotalDelProducto = (totalPProveedor - new.cantidad)
                where idProductoProveedor = idPProveedor;
			end $$
Delimiter ;

Delimiter $$
	create procedure sp_eliminarDetalleFactura(in spcodigoDetalleFactura int)
		begin
        declare total decimal(10,2);
			declare precioTotal decimal(10,2);
			declare resultado decimal(10,2);
			declare idPProveedor int;
			declare existencia int;
			declare idFactura int;
			declare idProducto varchar(15);
            declare cantidadProducto int;
			select numeroFactura into idFactura from DetalleFactura where codigoDetalleFactura = spcodigoDetalleFactura limit 1;
			select cantidad into cantidadProducto from DetalleFactura where codigoDetalleFactura = codigoDetalleFactura limit 1;
            select codigoProducto into idProducto from DetalleFactura where codigoDetalleFactura = codigoDetalleFactura limit 1;
			
            select idProductoProveedor into idPProveedor from Productos where codigoProducto = idProducto  limit 1;
			select existenciaTotalDelProducto into existencia from ProductoProveedor where idProductoProveedor = idPProveedor limit 1;

			select totalFactura into total from Factura where numeroFactura = idFactura;
			select (precioUnitario * cantidad) into precioTotal from DetalleFactura where codigoDetalleFactura = spcodigoDetalleFactura;
			set resultado = total - precioTotal;
				
			update Factura 
					set totalFactura = resultado 
			where idFactura = idFactura;
                
			update ProductoProveedor
			set existenciaTotalDelProducto = (existencia + cantidadProducto)
			where idProductoProveedor = idPProveedor;
                
			delete from DetalleFactura where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
		end $$
Delimiter ;



-- Producto Proveedor 
call sp_agregarTipoProducto("xd");

call sp_agregarCompras("2020-12-10","Legos", 12.00);

call sp_agregarProveedores("123412sK2","Jose Mario", "Larios Cante", "Zona 1", "Todo Publico","5025 4241", "kinal.com" );

call sp_agregarCliente("1209DF123","Ramiro Jose","Morales López"," 9av 13-70 zona 3","5327 6129","rmorales215327@gmail.com");

call sp_agregarCargoEmpleado("Administrador", "llevar informe");


call sp_agregarEmpleados(1,'Marlon','Carrilo','80.0','5 Calle','H',1);

call sp_agregarEmailProveedor(1,'kinal@kinal','antigua',1);

call sp_agregarTelefonoProveedor(1,'76297387','40910212','502',1);

call sp_agregarProductoProveedor("Ambar","Caja",110,15,2,0);
-- Productos 
-- call sp_agregarProductos("JD5BM","Jabon Ambar",0,0,0,"PNG",0,1,1,"123412sK2" );
-- call sp_agregarProductos(?,         ?,         ?,?,?, ?,   ?, ?, ?, ?)
-- DETALLE COMPRA
call sp_agregarDetalleCompra(1,0,2,"JD5BM",1);
-- Detalle Factura
call sp_agregarFactura(1,'Anonimo',4.0,'2022-11-06',1,1);


select * from DetalleFactura
join Factura on DetalleFactura.numeroFactura = Factura.numeroFactura
join Clientes on Factura.codigoCliente = Clientes.codigoCliente
join Productos on DetalleFactura.codigoProducto = Productos. codigoProducto
where Factura.numeroFactura = 1;