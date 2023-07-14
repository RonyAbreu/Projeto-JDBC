package model.dao.impl;

import db.DB;
import db.DbException;
import entities.Department;
import entities.Seller;
import model.dao.SellerDao;

import java.sql.*;
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
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("insert into seller (Name, Email, BirthDate, BaseSalary, DepartmentId) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1,s.getName());
            st.setString(2,s.getEmail());
            st.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
            st.setDouble(4,s.getBaseSalary());
            st.setInt(5,s.getDepartment().getId());

            int rowsAffect = st.executeUpdate();

            if (rowsAffect > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    s.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Nenhuma linha foi afetada!");
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void updateSeller(Seller s) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("update seller set Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? where Id = ?");

            st.setString(1,s.getName());
            st.setString(2,s.getEmail());
            st.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
            st.setDouble(4,s.getBaseSalary());
            st.setInt(5,s.getDepartment().getId());
            st.setInt(6, s.getId());

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
            st = conn.prepareStatement("delete from seller where Id = ?");

            st.setInt(1,id);

            int rowsAffect = st.executeUpdate();
            if (rowsAffect == 0){
                throw new DbException("Usuário não existe! Por favor insira um Id válido!");
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
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
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("select seller.*, department.Name as DepName from seller inner join department on seller.DepartmentId = department.Id order by Name");
            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){
                Department department = map.get(rs.getInt("DepartmentId"));
                if (department == null){
                    department = instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),department);
                }
                Seller seller = instatiateSeller(rs,department);
                sellerList.add(seller);
            }
            return sellerList;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
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
