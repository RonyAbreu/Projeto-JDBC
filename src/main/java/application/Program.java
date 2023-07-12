package application;

import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Department department = new Department(1,"Books");

        Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 2500.0, department);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(seller);
    }
}
