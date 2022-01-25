package model.DAO;

import java.util.List;

import model.entidades.Departamento;

public interface DepartamentoDAO {
	
	void inserir (Departamento departamento);
	void atualizar(Departamento departamento);
	void deletarById(Integer id);
	Departamento selecionarId(Integer id);
	List<Departamento> findAll();
	
	
	

}
