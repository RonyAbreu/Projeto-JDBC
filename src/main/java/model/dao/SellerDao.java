package model.dao;

import entities.Department;
import entities.EntitiesException;
import entities.Seller;

import java.util.List;

public interface SellerDao {
    void insertSeller(Seller s) throws EntitiesException;
    void updateSeller(Seller s) throws EntitiesException;
    void deleteById(Integer id) throws EntitiesException;
    Seller findById(Integer id) throws EntitiesException;
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department) throws EntitiesException;
}
