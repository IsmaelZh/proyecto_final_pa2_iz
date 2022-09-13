package com.uce.edu.demo.repository;

import java.util.List;

import com.uce.edu.demo.modelo.Vehiculo;
import com.uce.edu.demo.to.VehiculoTo;

public interface IVehiculoRepository {

	public void insertar(Vehiculo vehiculo);
	public void actualizar(Vehiculo vehiculo);
	public Vehiculo buscar(Integer id);
	public void eliminar(Integer id);

	public Vehiculo buscarPorPlaca(String placa);
	public List<Vehiculo> buscarPorMarcaModelo(String marca, String modelo);
	public List<Vehiculo> buscarPorMarca(String marca);
	public List<VehiculoTo> buscarVehiculoTo(String marca, String modelo);
}
