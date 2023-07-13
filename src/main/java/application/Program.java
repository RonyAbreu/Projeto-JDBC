package application;

import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);

        System.out.println("=== TEST 2: seller findByDepartment ===");
        Department department = new Department(2,null);
        List<Seller> sellerList = sellerDao.findByDepartment(department);
        for (Seller s: sellerList) {
            System.out.println(s);
        }
    }
}
