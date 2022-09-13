package com.uce.edu.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

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

@SpringBootTest
@Transactional
@Rollback(true)
class ProyectoFinalApplicationTests {
	
	@Autowired
	private IGestorClienteService gestorClienteService;

	@Autowired
	private IGestorReporteService gestorReporteService;

	@Autowired
	private IGestorEmpleadoService gestorEmpleadoService;

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IVehiculoService vehiculoService;

	@Autowired
	private IReservaService reservaService;
	
	@Test
	void prueba1() {
		Cliente cliente = new Cliente();
		cliente.setNombre("Jose");
		cliente.setApellido("Zhindon");
		cliente.setCedula("1744201365");
		cliente.setGenero("M");
		cliente.setRegistro("C");
		cliente.setFechaNacimiento(LocalDateTime.of(1999, 12, 29, 0, 0));
		
		this.clienteService.insertar(cliente);
		assertNotNull(this.clienteService.buscarPorCedula("1744201365"));
	}
	
	@Test
	void prueba2() {
		assertNotNull(this.clienteService.buscarPorApellido("Benavides"));
	}
	
	@Test
	void prueba3() {
		assertEquals("C", this.clienteService.buscarPorCedula("1750368084").getRegistro());
	}
	
	@Test
	void prueba4() {
		assertEquals("E", this.clienteService.buscarPorCedula("1733102651").getRegistro());
	}
	
	@Test
	void prueba5() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca("PQQ-1655");
		vehiculo.setModelo("Sedan");
		vehiculo.setMarca("Nissan");
		vehiculo.setEstado("D");
		vehiculo.setAnioFabricacion("2021");
		vehiculo.setPaisFabricacion("Corea");
		vehiculo.setCilindraje("1600");
		vehiculo.setAvaluo(new BigDecimal(5000));
		vehiculo.setValorDia(new BigDecimal(65));
		
		this.vehiculoService.insertar(vehiculo);
		assertNotNull(this.vehiculoService.buscarPorMarca("Nissan"));
	}
	
	@Test
	void prueba6() {
		assertNotNull(this.vehiculoService.buscarPorMarcaModelo("Ford", "Camioneta"));
	}
	
	@Test
	void prueba7() {
		assertEquals("Camioneta", this.vehiculoService.buscarPorPlaca("PQL-1234").getModelo());
	}
	
	@Test
	void prueba8() {
		List<VehiculoTo> listaVehiculos = this.gestorClienteService.buscarVehiculoToDisponble("Ford", "Camioneta");
		assertNotEquals(0, listaVehiculos.size());
	}
	
	@Test
	void prueba9() { 
		assertNotNull(this.reservaService.buscarPorNumero("13"));
	}
	
	@Test
	void prueba10() {
		List<ReservaTo> listaVehiculos = this.gestorReporteService.reporteReservas(LocalDateTime.of(2022, 9, 1, 0, 0), LocalDateTime.of(2022, 9, 30, 0, 0));
		assertNotNull(listaVehiculos);
	}
	
	@Test
	void prueba11() {
		List<ClienteVipTo> listaClientes = this.gestorReporteService.reporteClientesVip();
		assertNotEquals(0, listaClientes.size());
	}
	
	@Test
	void prueba12() {
		List<VehiculoVipTo> listaVehiculos = this.gestorReporteService.reporteVehiculosVip(Month.SEPTEMBER, Year.of(2022));
		assertNotEquals(0, listaVehiculos.size());
	}
	
	@Test
	void prueba13() {
		Cliente cliente = this.clienteService.buscarPorCedula("1750368084");
		Vehiculo vehiculo = this.vehiculoService.buscarPorPlaca("PLQ-9876");
		Reserva reserva = this.gestorClienteService.calcularValores("51105454534", 
				vehiculo, cliente, LocalDateTime.of(2022, 5, 1, 0, 0), LocalDateTime.of(2022, 5, 10, 0, 0));
		assertNotNull(reserva);
	}
	
	@Test
	void prueba14() {
		assertNull(this.vehiculoService.buscarPorPlaca("PLQ-987"));
	}
	
	@Test
	void prueba15() {
		
	}
	
	@Test
	void prueba16() {
		
	}
	
	@Test// P17
	void prueba17() {
		Vehiculo v1 = new Vehiculo();
		v1.setAnioFabricacion("2000");
		v1.setAvaluo(new BigDecimal(10000));
		v1.setCilindraje("1.5");
		v1.setEstado("D");
		v1.setPlaca("PKL-1234");
		v1.setModelo("Suv");
		v1.setMarca("Honda");
		v1.setPaisFabricacion("Japon");
		this.vehiculoService.insertar(v1);
		
		assertNotNull(this.vehiculoService.buscarPorPlaca("PKL-1234"));
		
	}
	
	
	@Test// P17
	void prueba18() {
		
		Cliente c = new Cliente();
		c.setApellido("Montoya");
		c.setCedula("1727945623");
		c.setFechaNacimiento(LocalDateTime.of(1990, 2, 3, 0, 0));
		c.setGenero("M");
		c.setNombre("Pepe");
		c.setRegistro("C");
		this.clienteService.insertar(c);
		c.setApellido("Mantelo");
		this.gestorEmpleadoService.actualizar(c);
		
		assertNotEquals(c.getApellido(), "Mantelo");
		
	}
	@Test
	void prueba19() {
		
		Vehiculo v1 = new Vehiculo();
		v1.setAnioFabricacion("2000");
		v1.setAvaluo(new BigDecimal(10000));
		v1.setCilindraje("1.5");
		v1.setEstado("D");
		v1.setPlaca("PKL-1234");
		v1.setModelo("Suv");
		v1.setMarca("Honda");
		v1.setPaisFabricacion("Japon");
		this.vehiculoService.insertar(v1);
		v1.setEstado("ND");
		this.gestorEmpleadoService.actualizarVehiculo(v1);
		assertNotEquals(v1.getEstado(), "ND");
	}
	@Test
	void prueba20() {
		
		List<Vehiculo> vehiculo =  this.vehiculoService.buscarPorMarcaModelo  (" ", "");
		assertNull(vehiculo);
		
	}
	@Test
	void prueba21() {
			
			List<Vehiculo> vehiculo =  this.vehiculoService.buscarPorMarcaModelo  ("Ford", "Compacto");
			assertNull(vehiculo);
			
		}
	@Test
	void prueba22() {
			
			List<VehiculoTo> vehiculo =  this.vehiculoService.buscarVehiculoTo("", "");
			assertNull(vehiculo);
			
		}
	@Test
	void prueba23() {
		
		List<VehiculoTo> vehiculo =  this.vehiculoService.buscarVehiculoTo ("Ford", "Suv");
		assertNull(vehiculo);
		
	}
	
	
	@Test
	void prueba24() {
		
	}
	
	@Test
	void prueba25() {
		
	}
	
	@Test
	void prueba26() {
		
	}
	
	@Test
	void prueba27() {
		
	}
	
	@Test
	void prueba28() {
		
	}
	
	@Test
	void prueba29() {
		
	}
	
	@Test
	void prueba30() {
		
	}

}
