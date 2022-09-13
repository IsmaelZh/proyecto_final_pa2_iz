package com.uce.edu.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.uce.edu.demo.modelo.Reserva;

public interface IReservaService {

	public void insertar(Reserva reserva);
	public void actualizar(Reserva reserva);
	public Reserva buscarPorId(Integer id);
	public void eliminar(Integer id);
	
	public Reserva buscarReservaDisponibleFechas(String placa, LocalDateTime fechaInicio,LocalDateTime fechaFin);
	public Reserva buscarPorNumero(String numero);
	public List<Reserva> reporteReservas();
}
