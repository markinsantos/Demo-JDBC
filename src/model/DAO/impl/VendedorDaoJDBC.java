package model.DAO.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import db.DB;
import db.DbException;
import model.DAO.VendedorDAO;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class VendedorDaoJDBC implements VendedorDAO{

	private Connection conn;
	
	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void inserir(Vendedor vendedor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar(Vendedor vendedor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletarById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vendedor selecionarId(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs =null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id "
							+ "WHERE DepartmentId = ? ");
			
			st.setInt(1,id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				
				Departamento dep = new Departamento();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setNome(rs.getString("DepName"));
				
				Vendedor vd = new Vendedor();
				
				vd.setId(rs.getInt("Id"));
				vd.setNome(rs.getString("Name"));
				vd.setEmail(rs.getString("Email"));
				vd.setSalarioBase(rs.getDouble("BaseSalary"));
				vd.setDateNascimento(rs.getDate("BirthDate"));
				vd.setDepartamento(dep);
				return vd;
								
			}
			return null;
		}catch(SQLException e){
			throw new DbException(e.getMessage());
			
		}finally {
			
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
	}

	@Override
	public List<Vendedor> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
