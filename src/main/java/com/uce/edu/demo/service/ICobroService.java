package com.uce.edu.demo.service;

import com.uce.edu.demo.modelo.Cobro;
import com.uce.edu.demo.modelo.Reserva;

public interface ICobroService {
	
	public void insertar(Cobro cobro);
	public Cobro buscar(Integer id);

	public Cobro crearCobro(String tarjeta, Reserva reserva);

}
