package main;

import java.util.Date;

import model.DAO.FactoryDAO;
import model.DAO.VendedorDAO;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class Programa {
	
public static void main(String[] args) {
		
	
	VendedorDAO vendedorDao = FactoryDAO.criarVendedorDAO();
	Vendedor vendedor = vendedorDao.selecionarId(3);
	
	
		System.out.println(vendedor);
		
		}

}
