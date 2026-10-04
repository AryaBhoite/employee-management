package com.example.employee.service;

import com.example.employee.entity.Department;
import com.example.employee.exception.DepartmentNotFoundException;
import com.example.employee.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

    public Department createDepartment(Department department) {
        return repository.save(department);
    }

    public Department getDepartmentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                                "Department not found with id: " + id
                        )
                );
    }
}