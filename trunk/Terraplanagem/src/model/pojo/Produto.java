package model.pojo;

public class Produto {
	private Long id;
	private String descricao;
	private double pCusto;
	private double densidade;
	private double pVista;
	private double pPrazo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getDensidade() {
		return densidade;
	}
	public void setDensidade(double densidade) {
		this.densidade = densidade;
	}
	public double getpCusto() {
		return pCusto;
	}
	public void setpCusto(double pCusto) {
		this.pCusto = pCusto;
	}
	public double getpVista() {
		return pVista;
	}
	public void setpVista(double pVista) {
		this.pVista = pVista;
	}
	public double getpPrazo() {
		return pPrazo;
	}
	public void setpPrazo(double pPrazo) {
		this.pPrazo = pPrazo;
	}	
}
