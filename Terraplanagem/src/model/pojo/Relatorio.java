package model.pojo;


public class Relatorio {
	
	private Long numeroPedido;
	private String dataPedido;
	private double valorPedido;
	private String pagamento;
	private String localPedido;
//	private ArrayList<ItemPedido> produtosPedido;
	private String cliente;
	
	public Long getNumeroPedido() {
		return numeroPedido;
	}
	public void setNumeroPedido(Long numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	public String getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(String dataPedido) {
		this.dataPedido = dataPedido;
	}
	public double getValorPedido() {
		return valorPedido;
	}
	public void setValorPedido(double valorPedido) {
		this.valorPedido = valorPedido;
	}
//	public ArrayList<ItemPedido> getProdutosPedido() {
//		return produtosPedido;
//	}
//	public void setProdutosPedido(ArrayList<ItemPedido> produtosPedido) {
//		this.produtosPedido = produtosPedido;
//	}
	public String getLocalPedido() {
		return localPedido;
	}
	public void setLocalPedido(String localPedido) {
		this.localPedido = localPedido;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getPagamento() {
		return pagamento;
	}
	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

}
