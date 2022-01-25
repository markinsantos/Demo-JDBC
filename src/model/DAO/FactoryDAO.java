package model.DAO;

import db.DB;
import model.DAO.impl.VendedorDaoJDBC;

public class FactoryDAO {
	
	public static VendedorDAO criarVendedorDAO() {
		return new VendedorDaoJDBC(DB.getConnection());
	}

}
