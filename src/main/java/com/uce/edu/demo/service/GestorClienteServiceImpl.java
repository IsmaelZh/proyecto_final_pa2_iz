package com.uce.edu.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.edu.demo.modelo.Cliente;
import com.uce.edu.demo.modelo.Cobro;
import com.uce.edu.demo.modelo.Reserva;
import com.uce.edu.demo.modelo.Vehiculo;
import com.uce.edu.demo.to.VehiculoTo;

@Service
public class GestorClienteServiceImpl implements IGestorClienteService {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IReservaService reservaService;

	@Autowired
	private IVehiculoService vehiculoService;

	@Override
	@Transactional
	public Reserva calcularValores(String numeroTarjeta, Vehiculo vehiculo, Cliente cliente, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		Integer numeroDiasReserva = fechaFin.getDayOfMonth() - fechaInicio.getDayOfMonth();
		Reserva reserva = new Reserva();
		if (numeroTarjeta.equals("")) {
			return null;
		} else {
			BigDecimal valorSubtotal = vehiculo.getValorDia().multiply(BigDecimal.valueOf(numeroDiasReserva + 1));
			BigDecimal valorIva = (valorSubtotal.multiply(BigDecimal.valueOf(12))).divide(BigDecimal.valueOf(100));
			BigDecimal valorTotalPagar = valorSubtotal.add(valorIva);
			
			reserva.setFechaInicio(fechaInicio);
			reserva.setFechaFin(fechaFin);
			reserva.setEstado("G");
			reserva.setVehiculo(vehiculo);
			reserva.setCliente(cliente);
			
			Cobro cobro = new Cobro();
			cobro.setTarjeta(numeroTarjeta);
			cobro.setFechaCobro(LocalDateTime.now());
			cobro.setValorSubtotal(valorSubtotal);
			cobro.setValorIva(valorIva);
			cobro.setValorTotalPagar(valorTotalPagar);
			cobro.setReserva(reserva);
			
			reserva.setCobro(cobro);
			this.reservaService.insertar(reserva);
			
			reserva.setNumero(reserva.getId().toString());
			this.reservaService.actualizar(reserva);
		}
		return reserva;
	}

	@Override
	public LocalDateTime buscarFechaDisponible(Reserva reserva) {
		return reserva.getFechaFin().plusDays(1);
	}

	@Override
	public void registrarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		cliente.setRegistro("C");
		this.clienteService.insertar(cliente);
	}
	
	@Override
	public void actualizarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		cliente.setRegistro("C");
		Cliente c1 = this.clienteService.buscar(cliente.getId());
		cliente.setCedula(c1.getCedula());
		this.clienteService.actualizar(cliente);
	}
	
	@Override
	public List<VehiculoTo> buscarVehiculoToDisponble(String marca, String modelo) {
		// TODO Auto-generated method stub
		List<VehiculoTo> listaVehiculos = this.vehiculoService.buscarVehiculoTo(marca, modelo);
		return listaVehiculos;
	}

}
