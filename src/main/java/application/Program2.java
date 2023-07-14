package application;

import entities.Department;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;

public class Program2 {
    public static void main(String[] args) {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: department insert ===");
        Department department = new Department(null,"Geek");
        departmentDao.insertDepartment(department);
        System.out.println("Departamento inserido com sucesso!");

        System.out.println("=== TEST 2: department update ===");

        System.out.println("=== TEST 3: department delete ===");

        System.out.println("=== TEST 4: department findById ===");

        System.out.println("=== TEST 5: department findAll ===");

    }
}
