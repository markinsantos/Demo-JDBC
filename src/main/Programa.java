package main;


import java.util.Date;
import java.util.List;

import model.DAO.FactoryDAO;
import model.DAO.VendedorDAO;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class Programa {
	
public static void main(String[] args) {
		
		VendedorDAO vendedorDao = FactoryDAO.criarVendedorDAO();
		
		System.out.println("=== Teste 1===");
		Vendedor vendedor = vendedorDao.selecionarId(3);
		System.out.println(vendedor);
		
		System.out.println("=== Teste 2 ===");
		Departamento departamento = new Departamento(2,null);
		List<Vendedor> lista = vendedorDao.findByDepartamento(departamento);
		
		for(Vendedor vd : lista) {
			System.out.println(vd);
		}
		
		System.out.println("=== Teste 3 findAll ===");
		
		List<Vendedor> lista1 = vendedorDao.findAll();
		
		for(Vendedor vd : lista1) {
			System.out.println(vd);
		}
		/*System.out.println("=== Teste 4 ===");
		
		Vendedor vend = new Vendedor(null,"marks","marks@gmail.com", new Date(),4070.00,departamento);
				vendedorDao.inserir(vend);*/
				
		System.out.println("=== Teste 5 update  ===");	
		
		vendedor = vendedorDao.selecionarId(1);
		
				
		vendedorDao.deletarById(8);
		
		
				
		}


}
