package model.pojo;

import java.sql.Date;

public class Pedido {
	private Long id;
	private Long clienteId;
	private Date data;
	private double vTotal;
	private boolean pagEfetuado;
	private String localEntrega;
	private boolean tipo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public double getvTotal() {
		return vTotal;
	}
	public void setvTotal(double vTotal) {
		this.vTotal = vTotal;
	}
	public boolean isPagEfetuado() {
		return pagEfetuado;
	}
	public void setPagEfetuado(boolean pagEfetuado) {
		this.pagEfetuado = pagEfetuado;
	}
	public String getLocalEntrega() {
		return localEntrega;
	}
	public void setLocalEntrega(String localEntrega) {
		this.localEntrega = localEntrega;
	}
	public boolean isTipo() {
		return tipo;
	}
	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
	
	
}
