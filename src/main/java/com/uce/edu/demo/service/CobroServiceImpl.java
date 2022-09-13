package com.uce.edu.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.edu.demo.modelo.Cobro;
import com.uce.edu.demo.modelo.Reserva;
import com.uce.edu.demo.repository.ICobroRepository;

@Service
public class CobroServiceImpl implements ICobroService {

	@Autowired
	private ICobroRepository cobroRepository;
	
	@Override
	public void insertar(Cobro cobro) {
		// TODO Auto-generated method stub
		this.cobroRepository.insertar(cobro);
	}

	@Override
	public Cobro buscar(Integer id) {
		// TODO Auto-generated method stub
		return this.cobroRepository.buscar(id);
	}
	
	@Override
	public Cobro crearCobro(String tarjeta, Reserva reserva) {
		// TODO Auto-generated method stub
		return this.cobroRepository.crearCobro(tarjeta, reserva);
	}

}
