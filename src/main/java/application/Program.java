package application;

import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);
////
        System.out.println("=== TEST 2: seller findByDepartment ===");
        Department department = new Department(2, "Roblox");
        List<Seller> sellerList = sellerDao.findByDepartment(department);
        sellerList.forEach(name -> System.out.println(name));
////
        System.out.println("=== TEST 3: seller findAll ===");
        List<Seller> sellerAll = sellerDao.findAll();
        sellerAll.forEach(System.out::println);

        System.out.println("=== TEST 4: seller insert ===");
        Seller newSeller = new Seller(null, "Marcos", "greg@gmail.com", new Date(), 4000.0, department);
        sellerDao.insertSeller(newSeller);
        System.out.println("Inserido! Novo id = " + newSeller.getId());

//        System.out.println("=== TEST 5: seller update ===");
//        seller = sellerDao.findById(1);
//        seller.setName("Bob Waine");
//        sellerDao.updateSeller(seller);
//        System.out.println("Usuário atualizado!");
//
//        System.out.println("=== TEST 6: seller delete ===");
//        System.out.println("Usuário sendo deletado: " + sellerDao.findById(11));
//        sellerDao.deleteById(11);
    }
}
