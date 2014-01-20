package model.pojo;


public class ItemPedido {
	private Long id;
	private double quantidade;
	private double pUnitario;
	private double pTotal;
	private Long pedidoId;
	private Long produtoId;
	private double pesoCaminhao;
	private double densidade;
	private String descricao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	public double getpUnitario() {
		return pUnitario;
	}
	public void setpUnitario(double pUnitario) {
		this.pUnitario = pUnitario;
	}
	public double getpTotal() {
		return pTotal;
	}
	public void setpTotal(double pTotal) {
		this.pTotal = pTotal;
	}
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public Long getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}
	public double getPesoCaminhao() {
		return pesoCaminhao;
	}
	public void setPesoCaminhao(double pesoCaminhao) {
		this.pesoCaminhao = pesoCaminhao;
	}
	public double getDensidade() {
		return densidade;
	}
	public void setDensidade(double densidade) {
		this.densidade = densidade;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
