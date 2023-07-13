package model.dao.impl;

import db.DB;
import db.DbException;
import entities.Department;
import entities.Seller;
import model.dao.SellerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertSeller(Seller s) {

    }

    @Override
    public void updateSeller(Seller s) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("select seller.*,department.Name as DepName from seller inner join department on seller.DepartmentId = department.Id where seller.Id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()){
                Department dep = instatiateDepartment(rs);
                Seller sel = instatiateSeller(rs,dep);
                return sel;
            }
            return null;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("select seller.*,department.Name as DepName from seller inner join department on seller.DepartmentId = department.Id where DepartmentId = ? order by Name");
            st.setInt(1, department.getId());

            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null){
                    dep = instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }

                Seller sel = instatiateSeller(rs,dep);
                sellerList.add(sel);
            }
            return sellerList;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sel = new Seller();
        sel.setId(rs.getInt("Id"));
        sel.setName(rs.getString("Name"));
        sel.setEmail(rs.getString("Email"));
        sel.setBirthDate(rs.getDate("BirthDate"));
        sel.setBaseSalary(rs.getDouble("BaseSalary"));
        sel.setDepartment(dep);
        return sel;
    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
}
