package com.uce.edu.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.uce.edu.demo.modelo.Cliente;
import com.uce.edu.demo.modelo.Reserva;
import com.uce.edu.demo.modelo.Vehiculo;
import com.uce.edu.demo.to.VehiculoTo;

public interface IGestorClienteService {

	public List<VehiculoTo> buscarVehiculoToDisponble(String marca, String modelo);
	public void registrarCliente(Cliente cliente);
	public void actualizarCliente(Cliente cliente);
	public Reserva calcularValores(String numeroTarjeta, Vehiculo vehiculo, Cliente cliente, LocalDateTime fechaInicio, LocalDateTime fechaFin);
	public LocalDateTime buscarFechaDisponible(Reserva reserva);
}
