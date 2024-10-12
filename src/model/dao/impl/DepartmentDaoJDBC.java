package model.dao.impl;

import java.sql.ResultSet;
import java.sql.Statement;
import db.DB;
import db.DbException;
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentDaoJDBC implements DepartmentDao {
    
    private Connection conn;
    
    public DepartmentDaoJDBC (Connection conn){
        this.conn = conn;
    }
    
    
    @Override
    public void insert(Department obj) {
        PreparedStatement st = null; 
        try{
            st = conn.prepareStatement(
                    "INSERT INTO department (Name) "
                    + "VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                System.out.println("Insert sucessfull!");
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Insert fail, department not created!");
            }
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deletById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT Name as depName, Id as depId "
                    + "FROM department "
                    + "where Id = ?");     
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){
                Department dep = instantiateDepartment(rs);
                return dep;
            }
            return null;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Department instantiateDepartment(ResultSet rs) throws SQLException{
        int depId = rs.getInt("depId");
        String depName = rs.getString("depName");
        Department dep = new Department(depId, depName);
        return dep;
    }
    
}
