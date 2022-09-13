package com.uce.edu.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.edu.demo.modelo.Cliente;
import com.uce.edu.demo.modelo.Reserva;
import com.uce.edu.demo.modelo.Vehiculo;

@Service
public class GestorEmpleadoServiceImpl implements IGestorEmpleadoService {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IReservaService reservaService;

	@Autowired
	private IVehiculoService vehiculoService;

	@Override
	public void registrarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		cliente.setRegistro("E");
		this.clienteService.insertar(cliente);
	}

	@Override
	@Transactional
	public void ingresarVehiculo(Vehiculo vehiculo) {
		// TODO Auto-generated method stub
		vehiculo.setEstado("D");
		this.vehiculoService.insertar(vehiculo);
	}

	@Override
	@Transactional
	public boolean retirarVehiculoReservado(String numeroReserva) {
		// TODO Auto-generated method stub
		boolean validarRetirar = false;
		
		try {
			Reserva reserva = this.reservaService.buscarPorNumero(numeroReserva);
			Vehiculo vehiculo = reserva.getVehiculo();
			if ((reserva.getEstado().equals("G")) && (vehiculo.getEstado().equals("D"))) { //si reserva es estado:generado y vehiculo es estado:disponible
				reserva.setEstado("E");
				vehiculo.setEstado("ND");
				
				this.reservaService.actualizar(reserva);
				this.vehiculoService.actualizar(vehiculo);
				validarRetirar = true;
			}
		}catch (NullPointerException e) {
			return validarRetirar;
		}
		return validarRetirar;
	}

	@Override
    public void actualizar(Cliente cliente) {
        // TODO Auto-generated method stub
        cliente.setRegistro("E");
        this.clienteService.actualizar(cliente);
    }

    @Override
    public void actualizarVehiculo(Vehiculo vehiculo) {
        // TODO Auto-generated method stub
        vehiculo.setEstado("D");
        this.vehiculoService.actualizar(vehiculo);
    }

}
