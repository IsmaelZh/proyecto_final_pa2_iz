package com.uce.edu.demo.to;

import java.math.BigDecimal;

public class VehiculoVipTo {

	private Integer id;
	private String placa;
	private String modelo;
	private String marca;
	private BigDecimal valorSubtotal;
	private BigDecimal valorTotalPagar;
	
	public VehiculoVipTo() {
		// TODO Auto-generated constructor stub
	}

	public VehiculoVipTo(Integer id, String placa, String modelo, String marca, BigDecimal valorSubtotal,
			BigDecimal valorTotalPagar) {
		super();
		this.id = id;
		this.placa = placa;
		this.modelo = modelo;
		this.marca = marca;
		this.valorSubtotal = valorSubtotal;
		this.valorTotalPagar = valorTotalPagar;
	}
	
	@Override
	public String toString() {
		return "VehiculoVipTo [id=" + id + ", placa=" + placa + ", modelo=" + modelo + ", marca=" + marca
				+ ", valorSubtotal=" + valorSubtotal + ", valorTotalPagar=" + valorTotalPagar + "]";
	}

	//SET y GET
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public BigDecimal getValorSubtotal() {
		return valorSubtotal;
	}

	public void setValorSubtotal(BigDecimal valorSubtotal) {
		this.valorSubtotal = valorSubtotal;
	}

	public BigDecimal getValorTotalPagar() {
		return valorTotalPagar;
	}

	public void setValorTotalPagar(BigDecimal valorTotalPagar) {
		this.valorTotalPagar = valorTotalPagar;
	}

}
