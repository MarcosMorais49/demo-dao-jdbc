package model.dao.impl;

import java.sql.ResultSet;
import java.sql.Statement;
import db.DB;
import db.DbException;
import db.DbIntegrityException;
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
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                System.out.println("Insert sucessfull!");
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
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
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE department "
                    + "SET Name = ? "
                    + "WHERE Id = ?");
            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0){
                System.out.println("Update completed");
            }
            else {
                System.out.println("Update not completed");
            }
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE "
                    + "FROM department "
                    + "WHERE Id = ?");
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0){
                System.out.println(rowsAffected + " Department(s) deleted!");
            }
            else {
                throw new DbException("Invalid data, no items deleted!");
            }
        }
        catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
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
        List<Department> list = new ArrayList<>();
        
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try{
            st = conn.prepareStatement("SELECT Id as depId, Name as depName FROM department");
            rs = st.executeQuery();
            while (rs.next()){
                Department dep = instantiateDepartment(rs);
                list.add(dep);
            }
            return list;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }
    
    public Department instantiateDepartment(ResultSet rs) throws SQLException{
        int depId = rs.getInt("depId");
        String depName = rs.getString("depName");
        Department dep = new Department(depId, depName);
        return dep;
    }
    
}
