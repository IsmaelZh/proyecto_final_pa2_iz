package com.uce.edu.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.uce.edu.demo.modelo.Reserva;

public interface IReservaRepository {

	public void insertar(Reserva reserva);
	public void actualizar(Reserva reserva);
	public Reserva buscar(Integer id);
	public void eliminar(Integer id);

	public Reserva buscarReservaDisponibleFechas(String placa, LocalDateTime fechaInicio,LocalDateTime fechaFin);
	public Reserva buscarPorNumero(String numero);
	public List<Reserva> reporteReservas();
}
