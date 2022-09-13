package com.uce.edu.demo.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.edu.demo.modelo.Reserva;
import com.uce.edu.demo.to.ClienteVipTo;
import com.uce.edu.demo.to.ReservaTo;
import com.uce.edu.demo.to.VehiculoVipTo;

@Service
public class GestorReporteServiceImpl implements IGestorReporteService {

	@Autowired
	private IReservaService reservaService;

	@Override
	public List<ReservaTo> reporteReservas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		// TODO Auto-generated method stub
		List<Reserva> listaReportes = this.reservaService.reporteReservas();		
		List<ReservaTo> listaProcesada = listaReportes.parallelStream()
				.filter(fecha -> (fecha.getFechaInicio().isEqual(fechaFin) || fecha.getFechaInicio().isBefore(fechaFin))
						&& (fecha.getFechaInicio().isEqual(fechaInicio) || fecha.getFechaInicio().isAfter(fechaInicio)))
				.map(reservaTo -> {
					ReservaTo reserva = new ReservaTo();
					reserva.setId(reservaTo.getId());
					reserva.setFechaInicio(reservaTo.getFechaInicio());
					reserva.setFechaFin(reservaTo.getFechaFin());
					reserva.setEstado(reservaTo.getEstado());
					reserva.setCedula(reservaTo.getCliente().getCedula());
					reserva.setNombre(reservaTo.getCliente().getNombre());
					reserva.setApellido(reservaTo.getCliente().getApellido());
					reserva.setPlaca(reservaTo.getVehiculo().getPlaca());
					reserva.setModelo(reservaTo.getVehiculo().getModelo());
					reserva.setMarca(reservaTo.getVehiculo().getMarca());
					return reserva;
				}).collect(Collectors.toList());
		return listaProcesada;
	}
	
	@Override
	public List<ClienteVipTo> reporteClientesVip() {
		// TODO Auto-generated method stub
		List<Reserva> listaReportes = this.reservaService.reporteReservas();		
		Stream<ClienteVipTo> lista = listaReportes.parallelStream().map(cliente -> {
			ClienteVipTo clienteTo = new ClienteVipTo();
			clienteTo.setId(cliente.getCliente().getId());
			clienteTo.setCedula(cliente.getCliente().getCedula());
			clienteTo.setNombre(cliente.getCliente().getNombre());
			clienteTo.setApellido(cliente.getCliente().getApellido());
			clienteTo.setValorIva(cliente.getCobro().getValorIva());
			clienteTo.setValorTotalPagar(cliente.getCobro().getValorTotalPagar());
			return clienteTo;
		});

		List<ClienteVipTo> listaConvertida = lista.collect(Collectors.toList());
		List<ClienteVipTo> listaVipTo = listaConvertida.parallelStream()
				.collect(Collectors.groupingBy(ClienteVipTo::getId)).entrySet().parallelStream()
				.map(cliente -> cliente.getValue().parallelStream()
						.reduce((clientePrimer, clienteSegundo) -> new ClienteVipTo(clientePrimer.getId(), clientePrimer.getCedula(),
								clientePrimer.getNombre(), clientePrimer.getApellido(),
								clientePrimer.getEdad(),
								clientePrimer.getValorIva().add(clienteSegundo.getValorIva()),
								clientePrimer.getValorTotalPagar().add(clienteSegundo.getValorTotalPagar()))))
				.map(cliente -> cliente.get()).sorted(Comparator.comparing(ClienteVipTo::getValorTotalPagar).reversed())
				.collect(Collectors.toList());
		
		for (ClienteVipTo cliente : listaVipTo) {
			System.out.println(cliente);
		}
		return listaVipTo;
	}

	@Override
	public List<VehiculoVipTo> reporteVehiculosVip(Month mes, Year anio) {
		// TODO Auto-generated method stub
		List<Reserva> listaReporte = this.reservaService.reporteReservas();
		int mesLambda = mes.getValue();
		int anioLambda = anio.getValue();
		
		Stream<VehiculoVipTo> lista = listaReporte.parallelStream()
				.filter(reporte -> (reporte.getCobro().getFechaCobro().getMonthValue() == mesLambda)
						&& (reporte.getCobro().getFechaCobro().getYear() == anioLambda))
				.map(vehiculo -> {
					VehiculoVipTo vehiculoTo = new VehiculoVipTo();
					vehiculoTo.setId(vehiculo.getVehiculo().getId());
					vehiculoTo.setPlaca(vehiculo.getVehiculo().getPlaca());
					vehiculoTo.setMarca(vehiculo.getVehiculo().getMarca());
					vehiculoTo.setModelo(vehiculo.getVehiculo().getModelo());
					vehiculoTo.setValorSubtotal(vehiculo.getCobro().getValorSubtotal());
					vehiculoTo.setValorTotalPagar(vehiculo.getCobro().getValorTotalPagar());

					return vehiculoTo;
				});
		
		return null;
	}

}
