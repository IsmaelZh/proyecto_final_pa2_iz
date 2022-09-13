package com.uce.edu.demo.to;


import java.time.LocalDateTime;

public class ReservaTo {

	private Integer id;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private String estado;
	private String placa;
	private String modelo;
	private String marca;
	private String nombre;
	private String apellido;
	private String cedula;
	
	public ReservaTo() {
		// TODO Auto-generated constructor stub
	}

	public ReservaTo(Integer id, LocalDateTime fechaInicio, LocalDateTime fechaFin, String estado, String placa,
			String modelo, String marca, String nombre, String apellido, String cedula) {
		super();
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.placa = placa;
		this.modelo = modelo;
		this.marca = marca;
		this.nombre = nombre;
		this.apellido = apellido;
		this.cedula = cedula;
	}
	
	@Override
	public String toString() {
		return "ReservaVipTo [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", estado="
				+ estado + ", placa=" + placa + ", modelo=" + modelo + ", marca=" + marca + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", cedula=" + cedula + "]";
	}

	//SET y GET
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

}
