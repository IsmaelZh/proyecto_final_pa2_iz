package com.uce.edu.demo.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.List;

import com.uce.edu.demo.to.ClienteVipTo;
import com.uce.edu.demo.to.ReservaTo;
import com.uce.edu.demo.to.VehiculoVipTo;

public interface IGestorReporteService {

	List<ReservaTo> reporteReservas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
	List<ClienteVipTo> reporteClientesVip();
	List<VehiculoVipTo> reporteVehiculosVip(Month mes, Year anio);
}
