package model.dao;

import entities.Seller;

import java.util.List;

public interface SellerDao {
    void insertSeller(Seller s);
    void updateSeller(Seller s);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
}
