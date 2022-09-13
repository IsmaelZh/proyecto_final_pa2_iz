package com.uce.edu.demo.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.edu.demo.modelo.Vehiculo;
import com.uce.edu.demo.repository.IVehiculoRepository;
import com.uce.edu.demo.to.VehiculoTo;

@Service
public class VehiculoServiceImpl implements IVehiculoService {

	@Autowired
	private IVehiculoRepository vehiculoRepository;
	
	@Override
	public void insertar(Vehiculo vehiculo) {
		// TODO Auto-generated method stub
		this.vehiculoRepository.insertar(vehiculo);
	}
	
	@Override
	@Transactional(value = TxType.SUPPORTS)
	public void actualizar(Vehiculo vehiculo) {
		// TODO Auto-generated method stub
		this.vehiculoRepository.actualizar(vehiculo);
	}

	@Override
	public Vehiculo buscar(Integer id) {
		// TODO Auto-generated method stub
		return this.vehiculoRepository.buscar(id);
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		this.vehiculoRepository.eliminar(id);
	}
	
	@Override
	public Vehiculo buscarPorPlaca(String placa) {
		// TODO Auto-generated method stub
		return this.vehiculoRepository.buscarPorPlaca(placa);
	}

	@Override
	public List<Vehiculo> buscarPorMarcaModelo(String marca, String modelo) {
		// TODO Auto-generated method stub
		return this.vehiculoRepository.buscarPorMarcaModelo(marca, modelo);
	}
	
	@Override
	public List<Vehiculo> buscarPorMarca(String marca) {
		// TODO Auto-generated method stub
		return this.vehiculoRepository.buscarPorMarca(marca);
	}

	@Override
	public List<VehiculoTo> buscarVehiculoTo(String marca, String modelo) {
		// TODO Auto-generated method stub
		return this.vehiculoRepository.buscarVehiculoTo(marca, modelo);
	}
	

}
