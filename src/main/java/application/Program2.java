package application;

import entities.Department;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;

import java.util.List;

public class Program2 {
    public static void main(String[] args) {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: department insert ===");
        Department department = new Department(null,"Geek");
        departmentDao.insertDepartment(department);
        System.out.println("Departamento inserido com sucesso!");

        System.out.println("=== TEST 2: department update ===");
        Department newDepartment = new Department(7,"Phantom");
        departmentDao.updateDepartment(newDepartment);
        System.out.println("Departamento atualizado com sucesso!");

        System.out.println("=== TEST 3: department delete ===");
        departmentDao.deleteById(8);
        System.out.println("Usuário Deletado");

        System.out.println("=== TEST 4: department findById ===");
        System.out.println("Usuário encontrado!");
        System.out.println(departmentDao.findById(1));

        System.out.println("=== TEST 5: department findAll ===");
        List<Department> departmentList = departmentDao.findAll();
        departmentList.forEach(System.out::println);

    }
}
