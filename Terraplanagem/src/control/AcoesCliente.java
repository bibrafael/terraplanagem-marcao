package control;

import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.pojo.Cliente;
import model.services.ServicosCliente;

public class AcoesCliente {
	
	private static AcoesCliente instance = null;
	
	public static AcoesCliente getInstance() {
		if(instance == null) {
			instance = new AcoesCliente();
		}
		return instance;
	}
	
	private Vector<String> colunas;
	
	private AcoesCliente() {
		colunas = new Vector<String>();
		colunas.add("Id");
		colunas.add("Nome");
		colunas.add("CPF/CNPJ");
		colunas.add("Endereco");
		colunas.add("Cidade");
		colunas.add("Telefone");
	}
	
	public DefaultTableModel criar(String nome, String endereco, String cidade, String cpf_cnpj, String telefone) {
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setEndereco(endereco);
		cliente.setCidade(cidade);
		cliente.setCpf_cnpj(cpf_cnpj);
		cliente.setTelefone(telefone);
		ServicosCliente.getInstance().criar(cliente);
		return (createModel(ServicosCliente.getInstance().ler(null)));
	}
	
	public DefaultTableModel ler(String nome) {
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		return (createModel(ServicosCliente.getInstance().ler(cliente)));
	}
	
	public DefaultTableModel deletar(Long id) {
		if(id != null) {
			ServicosCliente.getInstance().deletar(id);
		}
		return (createModel(ServicosCliente.getInstance().ler(null)));
	}
	
	public DefaultTableModel atualizar(Long id, String nome, String endereco, String cidade, String cpfCnpj, String telefone) {
		Cliente cliente = new Cliente();
		cliente.setId(id);
		cliente.setNome(nome);
		cliente.setEndereco(endereco);
		cliente.setCidade(cidade);
		cliente.setCpf_cnpj(cpfCnpj);
		cliente.setTelefone(telefone);
		ServicosCliente.getInstance().atualizar(cliente);
		return (createModel(ServicosCliente.getInstance().ler(null)));
	}
	
	public Cliente lerPorId(Long idCliente) {
		return (ServicosCliente.getInstance().lerPorId(idCliente));
	}
	
	private DefaultTableModel createModel(List<Cliente> clientes) {
		DefaultTableModel model = null;
		Vector<Vector<String>> dados = new Vector<Vector<String>>();
		if(clientes != null){
			for(int i=0; i < clientes.size();i++){
				Cliente aux = clientes.get(i);
				Vector<String> linha = new Vector<String>();
				linha.add(aux.getId().toString());
				linha.add(aux.getNome());
				linha.add(aux.getCpf_cnpj());
				linha.add(aux.getEndereco());
				linha.add(aux.getCidade());
				linha.add(aux.getTelefone());
				dados.add(linha);
			}
		}
		model = new DefaultTableModel(dados,colunas);
		return(model);
	};

}
