package model.dao.impl;

import db.DB;
import db.DbException;
import entities.Department;
import model.dao.DepartmentDao;

import java.sql.*;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertDepartment(Department d) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("insert into department (Name) values (?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1,d.getName());

            int rowsAffect = st.executeUpdate();
            if (rowsAffect > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    d.setId(id);
                }
                DB.closeResultSet(rs);
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void updateDepartment(Department d) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("update department set Name = ? where Id = ?");

            st.setString(1, d.getName());
            st.setInt(2, d.getId());

            int rowsAffect = st.executeUpdate();
            if (rowsAffect == 0){
                throw new DbException("Usuário inválido! Insira um Id válido");
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return null;
    }
}
