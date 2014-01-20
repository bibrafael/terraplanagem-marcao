package model.pojo;

public class Transporte {
	
	private Long id;
	private Long pedidoId;
	private double qHora;
	private double vHora;
	private double vTotal;
	private String placa;
	private String descricao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getqHora() {
		return qHora;
	}
	public void setqHora(double qHora) {
		this.qHora = qHora;
	}
	public double getvHora() {
		return vHora;
	}
	public void setvHora(double vHora) {
		this.vHora = vHora;
	}
	public double getvTotal() {
		return vTotal;
	}
	public void setvTotal(double vTotal) {
		this.vTotal = vTotal;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

}
