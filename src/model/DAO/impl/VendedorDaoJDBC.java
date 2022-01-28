package model.DAO.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, vendedor.getNome());
			st.setString(2, vendedor.getEmail());
			st.setDate(3, new java.sql.Date(vendedor.getDateNascimento().getTime()));
			st.setDouble(4, vendedor.getSalarioBase());
			st.setInt(5, vendedor.getDepartamento().getId());
			
			int rowsAffected = st.executeUpdate();
		
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					vendedor.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void atualizar(Vendedor vendedor) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
							"UPDATE seller "
							+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
							+ "WHERE Id = ?");
			
			st.setString(1, vendedor.getNome());
			st.setString(2, vendedor.getEmail());
			st.setDate(3, new java.sql.Date(vendedor.getDateNascimento().getTime()));
			st.setDouble(4, vendedor.getSalarioBase());
			st.setInt(5, vendedor.getDepartamento().getId());
			st.setInt(6, vendedor.getId());
			
			st.executeUpdate();
		
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deletarById(Integer id) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
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
				
				Departamento dep = instanciarDepartamento(rs);
				Vendedor vd = instanciarVendedor(rs, dep);
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

	public Vendedor instanciarVendedor(ResultSet rs, Departamento dep) throws SQLException {
		
		Vendedor vd = new Vendedor();
		vd.setId(rs.getInt("Id"));
		vd.setNome(rs.getString("Name"));
		vd.setEmail(rs.getString("Email"));
		vd.setSalarioBase(rs.getDouble("BaseSalary"));
		vd.setDateNascimento(rs.getDate("BirthDate"));
		vd.setDepartamento(dep);
		
		return vd;
	}

	public Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setNome(rs.getString("DepName"));
		
		
		return dep;
	}

	@Override
	public List<Vendedor> findAll() {
		PreparedStatement st = null;
		ResultSet rs =null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id "
							+ "ORDER BY Name");
			
	
			rs = st.executeQuery();
			List<Vendedor> lista = new ArrayList<Vendedor>();
			Map<Integer,Departamento> map = new HashMap<Integer, Departamento>();
			
			while(rs.next()) {
				
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep==null) {
				    dep = instanciarDepartamento(rs);
				    map.put(rs.getInt("DepartmentId"), dep);
				}
				Vendedor vd = instanciarVendedor(rs, dep);
				lista.add(vd);
								
			}
			return lista;
		}catch(SQLException e){
			throw new DbException(e.getMessage());
			
		}finally {
			
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Vendedor> findByDepartamento(Departamento departamento) {
		
		PreparedStatement st = null;
		ResultSet rs =null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id "
							+ "WHERE DepartmentId = ? "
							+ "ORDER BY Name");
			
			st.setInt(1,departamento.getId());
			rs = st.executeQuery();
			List<Vendedor> lista = new ArrayList<Vendedor>();
			Map<Integer,Departamento> map = new HashMap<Integer, Departamento>();
			
			while(rs.next()) {
				
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep==null) {
				    dep = instanciarDepartamento(rs);
				    map.put(rs.getInt("DepartmentId"), dep);
				}
				Vendedor vd = instanciarVendedor(rs, dep);
				lista.add(vd);
								
			}
			return lista;
		}catch(SQLException e){
			throw new DbException(e.getMessage());
			
		}finally {
			
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	
}
