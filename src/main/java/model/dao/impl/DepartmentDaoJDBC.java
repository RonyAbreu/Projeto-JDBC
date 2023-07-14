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
            st = conn.prepareStatement("insert into Department (Name) values (?)", Statement.RETURN_GENERATED_KEYS);

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
