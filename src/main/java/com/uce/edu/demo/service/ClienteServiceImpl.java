package com.uce.edu.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.edu.demo.modelo.Cliente;
import com.uce.edu.demo.repository.IClienteRepository;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteRepository clienteRepository;

	@Override
	public void insertar(Cliente cliente) {
		// TODO Auto-generated method stub
		this.clienteRepository.insertar(cliente);
	}

	@Override
	public void actualizar(Cliente cliente) {
		// TODO Auto-generated method stub
		this.clienteRepository.actualizar(cliente);
	}

	@Override
	public Cliente buscar(Integer id) {
		// TODO Auto-generated method stub
		return this.clienteRepository.buscar(id);
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		this.clienteRepository.eliminar(id);
	}

	@Override
	public Cliente buscarPorCedula(String cedula) {
		// TODO Auto-generated method stub
		return this.clienteRepository.buscarPorCedula(cedula);
	}
	
	@Override
	public List<Cliente> buscarPorApellido(String apellido) {
		// TODO Auto-generated method stub
		return this.clienteRepository.buscarPorApellido(apellido);
	}

}
