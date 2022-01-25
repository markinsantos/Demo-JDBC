package model.DAO;

import java.util.List;


import model.entidades.Vendedor;

public interface VendedorDAO {
	
	void inserir (Vendedor vendedor);
	void atualizar(Vendedor vendedor);
	void deletarById(Integer id);
	Vendedor selecionarId(Integer id);
	List<Vendedor> findAll();
	

}
