package model.dao.impl;

import db.DB;
import db.DbException;
import entities.Department;
import model.dao.DepartmentDao;

import java.sql.*;
import java.util.ArrayList;
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

            st.executeUpdate();
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("delete from department where id = ?");

            st.setInt(1,id);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0){
                throw new DbException("Usuário inválido!");
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("select * from department where Id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()){
                Department dep = instatiateDepartment(rs);
                return dep;
            } else {
                throw new DbException("Usuário inválido!");
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("select * from department");

            rs = st.executeQuery();

            List<Department> departmentList = new ArrayList<>();

            while (rs.next()){
                Department dep = instatiateDepartment(rs);
                departmentList.add(dep);
            }
            return departmentList;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }

}
