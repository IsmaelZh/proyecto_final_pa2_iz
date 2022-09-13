package com.uce.edu.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uce.edu.demo.modelo.Cliente;
import com.uce.edu.demo.modelo.Reserva;
import com.uce.edu.demo.modelo.Vehiculo;
import com.uce.edu.demo.service.IClienteService;
import com.uce.edu.demo.service.IGestorClienteService;
import com.uce.edu.demo.service.IGestorEmpleadoService;
import com.uce.edu.demo.service.IGestorReporteService;
import com.uce.edu.demo.service.IReservaService;
import com.uce.edu.demo.service.IVehiculoService;
import com.uce.edu.demo.to.ClienteVipTo;
import com.uce.edu.demo.to.ReservaTo;
import com.uce.edu.demo.to.VehiculoTo;
import com.uce.edu.demo.to.VehiculoVipTo;

@Controller
@RequestMapping("/budget")
public class BudgetController {

	@Autowired
	private IGestorReporteService gestorReporteService;

	@Autowired
	private IGestorClienteService gestorClienteService;

	@Autowired
	private IGestorEmpleadoService gestorEmpleadoService;

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IVehiculoService vehiculoService;

	@Autowired
	private IReservaService reservaService;

	private Cliente clienteReserva = new Cliente();
	private Vehiculo vehiculoReserva = new Vehiculo();
	private LocalDateTime fechaReservaInicio;
	private LocalDateTime fechaReservaFin;

	@GetMapping("/menu")
	public String menu() {
		return "vistaMenu";
	}

	// ------------Cliente--------------------------------
	// ------cliente busqueda
	@GetMapping("/menu/cliente>bus+vehi")
	public String menuClienteVB() {
		return "vistaClieBuscarVehiculo";
	}

	@GetMapping("/cliente>bus+vehi")
	public String clienteBusVehi(@RequestParam("marca") String marcaVehiculo,
			@RequestParam("modelo") String modeloVehiculo, Model modelo) {

		// buscar en la base con estos datos y mandar con el model a la siguente
		List<VehiculoTo> listaVehiculos = this.gestorClienteService.buscarVehiculoToDisponble(marcaVehiculo,
				modeloVehiculo);
		modelo.addAttribute("vehiculos", listaVehiculos);
		return "vistaClieListaVeiculosDis";
	}

	@GetMapping("/cliente>metodo+pago")
	public String clienteAct(@RequestParam("numTarjeta") String numTarjeta, @RequestParam("cvv") String cvv,
			Model modelo) {

		System.out.println("llego");
		Reserva reserva = this.gestorClienteService.calcularValores(numTarjeta, vehiculoReserva, clienteReserva,
				fechaReservaInicio, fechaReservaFin);
		if (reserva != null) {
			this.reservaService.insertar(reserva);
			modelo.addAttribute("reserva", reserva);
			return "vistaClieVentaRealizada";
		} else {
			return "vistaClieReservaFallida";
		}
	}

	// ----cliente reserva
	@GetMapping("/cliente>res+vehi")
	public String clienteResVehiculo(@RequestParam("placa") String placaVehiculo,
			@RequestParam("cedula") String cedulaCliente,
			@RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
			@RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
			Model modelo) {
		this.fechaReservaInicio = fechaInicio;
		this.fechaReservaFin = fechaFin;

		Cliente cliente = this.clienteService.buscarPorCedula(cedulaCliente);
		Vehiculo vehiculo = this.vehiculoService.buscarPorPlaca(placaVehiculo);
		if (cliente != null && vehiculo != null) {
			this.clienteReserva = cliente;
			this.vehiculoReserva = vehiculo;
			Reserva reserva = this.reservaService.buscarReservaDisponibleFechas(vehiculo.getPlaca(), fechaInicio,
					fechaFin);
			if (reserva == null) {
				return "vistaClieReservarVehiculo";
			} else {
				LocalDateTime fechaDisponible = this.gestorClienteService.buscarFechaDisponible(reserva);
				modelo.addAttribute("fecha", fechaDisponible); // cambiar a String si no funciona el LocalDateTime de
																// buscarFechaDisponible
				return "vistaClieResFechaInvalida";
			}
		} else {
			return "vistaClieResValorV";
		}
	}

	// ----cliente registro
	@GetMapping("/menu/cliente>registro")
	public String menuClienteReg(Model modelo) {
		modelo.addAttribute("hay", true);
		modelo.addAttribute("entrada", 1);
		return "vistaClieRegistrar";
	}

	@GetMapping("/cliente>registrar+clie")
	public String clieRegistrp(@RequestParam("cedula") String cedula, @RequestParam("nombre") String nombre,
			@RequestParam("apellido") String apellido,
			@RequestParam("fechaNacimiento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaNacimiento,
			@RequestParam("genero") String genero, Model modelo) {

		// verificas si hay cliente con esa cedula
		Cliente cliente = new Cliente();
		cliente.setCedula(cedula);
		cliente.setNombre(nombre);
		cliente.setApellido(apellido);
		cliente.setGenero(genero);
		cliente.setFechaNacimiento(fechaNacimiento);

		if (this.clienteService.buscarPorCedula(cliente.getCedula()) == null) {
			this.gestorClienteService.registrarCliente(cliente);
			modelo.addAttribute("hay", true);
		} else {
			modelo.addAttribute("hay", false);
		}
		modelo.addAttribute("entrada", 2);
		return "vistaClieRegistrar";
	}

	// --------actualizar
	@GetMapping("/menu/cliente>actualizar")
	public String menuClienteAct() {

		return "vistaClieBusCliente";
	}

	@GetMapping("/cliente>act+buscar")
	public String clienteAct(@RequestParam("cedula") String cedula, Model modelo) {
		// buscar cliente con CI
		Cliente cliente = this.clienteService.buscarPorCedula(cedula);

		// cliente=null;
		if (cliente != null) {
			modelo.addAttribute("cliente", cliente);
			modelo.addAttribute("hay", true);
		} else {
			modelo.addAttribute("hay", false);
		}
		return "vistaClieActualizar";
	}

	@PutMapping("/cliente>actualizado/{idCliente}")
	public String clienteActualizar(@PathVariable("idCliente") Integer id, Cliente cliente) {
		// this merge(cliente)
		cliente.setId(id);
		this.gestorClienteService.actualizarCliente(cliente);
		return "vistaClieActualizado";
	}

	// -------------------Empleado------------------------------

	// listo
	@GetMapping("/buscarUno/{idCliente}")
	public String buscarCliente(@PathVariable("idCliente") Integer id, Model modelo) {
		System.out.println("El ID: " + id);
		Cliente cliente = this.clienteService.buscar(id);
		modelo.addAttribute("cliente", cliente);
		return "vistaEmpActCliente";
	}

	// listo
	@PostMapping("/menu/registrarCliente")
	public String ERegistrarCliente(Cliente cliente) {

		this.clienteService.buscarPorCedula(cliente.getCedula());

		if (this.clienteService.buscarPorCedula(cliente.getCedula()) != null) {
			return "vistaEmpActError";
		}
		
		this.gestorEmpleadoService.registrarCliente(cliente);
		return "vistaEmpRegistrarCliente";
	}

	// listo vista de insertar datos
	@GetMapping("/menu/registrarCliente")
	public String buscarClientesEmpl(Cliente cliente) {

		return "vistaEmpRegistrarCliente";
	}

	// listo
	@GetMapping("/menu/empleado>bus+clie")
	public String menuEmpleadoBuscarCliente(Cliente cliente) {
		return "vistaEmpBusCliente";
	}

	// listo
	@GetMapping("/menu/empleado>lista+cliente")
	public String listaClientes(@RequestParam("apellido") String apellido, Model modelo) {

		List<Cliente> lista = this.clienteService.buscarPorApellido(apellido);
		modelo.addAttribute("clientes", lista);
		return "vistaEmpListaActBorCliente";

	}

	// listo
	@PutMapping("/actualizar/{idCliente}")
	public String EmpActCliente(@PathVariable("idCliente") Integer id, Cliente cliente, Model modelo) {
		Cliente clie = this.clienteService.buscarPorCedula(cliente.getCedula());

		if (clie != null) {// comprueba si las cedula modificada ya existe en la DB
			return "vistaEmpActError";// vista que muestre el error y boton de redireccion
		}
		Cliente clieActual = this.clienteService.buscar(id);
		cliente.setId(clieActual.getId());
		this.gestorEmpleadoService.actualizar(cliente);
		List<Cliente> lista = this.clienteService.buscarPorApellido(cliente.getApellido());
		modelo.addAttribute("clientes", lista);
		return "vistaEmpListaActBorCliente";
	}

	// listo
	@DeleteMapping("/eliminar/{idCliente}")
	public String eliminarCliente(@PathVariable("idCliente") Integer id, Model modelo) {
		Cliente cliente = this.clienteService.buscar(id);
		this.clienteService.eliminar(id);
		List<Cliente> lista = this.clienteService.buscarPorApellido(cliente.getApellido());
		System.out.println(lista);
		modelo.addAttribute("clientes", lista);
		return "vistaEmpListaActBorCliente";

	}
	
	@GetMapping("/buscarUnoVehiculo/{idVehiculo}")
	public String buscarVehiculo(@PathVariable("idVehiculo") String placa, Model modelo) {
		System.out.println("El ID: " + placa);
		Vehiculo vehiculo = this.vehiculoService.buscarPorPlaca (placa) ;
		modelo.addAttribute("vehiculo", vehiculo);
		return "vistaEmpActVehiculo";
	}
	
	
	//listo vista que ingresa y envia datos
	@PostMapping("/menu/empleado>ing+vehi")
	public String EIngresarVehiculo(Vehiculo vehiculo) {
		
		if(this.vehiculoService.buscarPorPlaca(vehiculo.getPlaca())!=null) {
			return "vistaEmpActError"; //AÃ±adir ventana para mostrar que ya existe esa placa
		}
		this.gestorEmpleadoService.ingresarVehiculo(vehiculo);
		return "vistaEmpIngresarVehiculo";
	}
	
	//listo: vista para ingresar datos
	@GetMapping("/menu/empleado>ing+vehi")
	public String menuEmpleadoIngVehiculo(Vehiculo vehiculo) {
		return "vistaEmpIngresarVehiculo";
	}

	//Listo: vista para ingresar marca 
	@GetMapping("/menu/empleado>bus+vehi")
	public String menuEmpleadoBusVehiculo(Vehiculo vehiculo) {
		return "vistaEmpBusVehiculo";
	}

	//listo: muestra la lista de marcas ingresadas 
	@GetMapping("/menu/empleado>lista+vehiculo")
	public String listaVehiculo(@RequestParam("marca") String marca, Model modelo) {
		System.out.println("El ID: " + marca);
		List<Vehiculo> lista = this.vehiculoService.buscarPorMarca(marca);
		System.out.println(lista);
		modelo.addAttribute("vehiculos", lista);
		return "vistaEmpListaActBorVehiculo";

	}
	
	@PutMapping("/actualizarVehiculo/{placaVehiculo}")
	public String EmpActVehiculo(@PathVariable("placaVehiculo") String placa, Vehiculo vehiculo, Model modelo) {
		//comprobacion
		Vehiculo v= this.vehiculoService.buscarPorPlaca(vehiculo.getPlaca());
		if(v!=null) {
			return "vistaMenu";
		}
		Vehiculo vehiActual = this.vehiculoService.buscarPorPlaca(placa);
			vehiculo.setId(vehiActual.getId());
			vehiculo.setPlaca (vehiculo.getPlaca());
			this.gestorEmpleadoService.actualizarVehiculo(vehiculo);
		List<Vehiculo> lista = this.vehiculoService.buscarPorMarca(vehiculo.getMarca());
		modelo.addAttribute("vehiculos", lista);
	
		return "vistaEmpListaActBorVehiculo";
	}

	@DeleteMapping("/eliminarVehiculo/{idVehiculo}")
	public String eliminarVehiculo(@PathVariable("idVehiculo") Integer id, Model modelo) {
		Vehiculo vehiculo = this.vehiculoService.buscar(id);
		this.vehiculoService.eliminar(id);
		List<Vehiculo> lista = this.vehiculoService.buscarPorMarca(vehiculo.getMarca());
		modelo.addAttribute("vehiculos", lista);
		return "vistaEmpListaActBorVehiculo";

	}

	// Retirar vehiculo
	@GetMapping("/menu/empleado>ret+vehi")
	public String menuEmpleadoRetVehiculo(Cliente cliente) {
		return "vistaEmpRetirarVehiculo";
	}

	@GetMapping("/empleado>ret+vehi")
	public String empleadoRetVehiculo(@RequestParam("numero") String numeroReserva, Model modelo) {
		boolean validarRetirar = this.gestorEmpleadoService.retirarVehiculoReservado(numeroReserva);
		modelo.addAttribute("validarRetirar", validarRetirar);
		// Comprobacion se hace en el html con un th:if
		return "vistaEmpRetiroRealizado";
	}

	// Retirar vehiculo sin reserva
	@GetMapping("/menu/empleado>ret+vehi=sr")
	public String menuEmpleadoRetVehiculoSR(Cliente cliente) {
		return "vistaEmpRetirarVehiculoSR";
	}

	// -------------------Reporte--------------------------------
	@GetMapping("/menu/reporte>reservas")
    public String menuReporteRes(Cliente cliente) {
        return "vistaRepReservas";
    }
	
	@GetMapping("/reporte>reservas")
    public String reportaListaRes(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            Model modelo) {
		
		List<ReservaTo> lista = this.gestorReporteService.reporteReservas(fechaInicio, fechaFin);
		System.out.println(lista);
        modelo.addAttribute("reportes", lista);
        return "vistaRepListaReservas";
    }
	
	@GetMapping("/menu/reporte>clie+vip")
    public String menuReporteClientesV(Model modelo) {
        List<ClienteVipTo> lista = this.gestorReporteService.reporteClientesVip();
        modelo.addAttribute("clienteVip", lista);
        System.out.println(lista);
        return "vistaRepClienteVip";

    }

    @GetMapping("/menu/reporte>vehi+vip")
    public String menuReporteVehiculoV() {
        return "vistaRepVehiculoVip";
    }

    @GetMapping("/listaVehiculoVip")
    public String reporteListaVehiVip(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, Model modelo) {
        List<VehiculoVipTo> lista = this.gestorReporteService.reporteVehiculosVip(fecha.getMonth(), Year.of(fecha.getYear()));
        modelo.addAttribute("vehiculoVip", lista);
        System.out.println(lista);
        return "vistaRepListaVehiVip";

    }
}
