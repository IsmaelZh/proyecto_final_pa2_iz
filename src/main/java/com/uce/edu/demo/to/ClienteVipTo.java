package com.uce.edu.demo.to;

import java.math.BigDecimal;

public class ClienteVipTo {

	private Integer id;
	private String cedula;
	private String nombre;
	private String apellido;
	private BigDecimal valorIva;
	private BigDecimal valorTotalPagar;
	private String edad;
	
	public ClienteVipTo() {
		// TODO Auto-generated constructor stub
	}
	
	public ClienteVipTo(Integer id, String cedula, String nombre, String apellido, String edad, BigDecimal valorIva,
			BigDecimal valorTotalPagar) {
		super();
		this.id = id;
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.valorIva = valorIva;
		this.valorTotalPagar = valorTotalPagar;
		this.edad = edad;
	}

	@Override
	public String toString() {
		return "ClienteVipTo [id=" + id + ", cedula=" + cedula + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", valorIva=" + valorIva + ", valorTotalPagar=" + valorTotalPagar + "]";
	}

	//SET y GET
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
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

	public BigDecimal getValorIva() {
		return valorIva;
	}

	public void setValorIva(BigDecimal valorIva) {
		this.valorIva = valorIva;
	}

	public BigDecimal getValorTotalPagar() {
		return valorTotalPagar;
	}

	public void setValorTotalPagar(BigDecimal valorTotalPagar) {
		this.valorTotalPagar = valorTotalPagar;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}
	
}
