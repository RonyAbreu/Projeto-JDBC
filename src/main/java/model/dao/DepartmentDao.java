package model.dao;

import entities.Department;

import java.util.List;

public interface DepartmentDao {

    void insertDepartment(Department d);
    void updateDepartment(Department d);
    void deleteById(Integer id);
    Department findById(Integer id);
    List<Department> findAll();
}
