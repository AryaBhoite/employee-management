package com.example.employee.service;

import com.example.employee.dto.EmployeeRequest;
import com.example.employee.dto.EmployeeResponse;
import com.example.employee.entity.Department;
import com.example.employee.entity.Employee;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.employee.exception.DepartmentNotFoundException;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(
            EmployeeRepository repository,
            DepartmentRepository departmentRepository) {

        this.repository = repository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setSalary(request.getSalary());

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found with id: "
                                        + request.getDepartmentId()
                        )
                );

        employee.setDepartment(department);

        Employee savedEmployee = repository.save(employee);

        return toResponse(savedEmployee);
    }
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        return toResponse(employee);
    }

    @Transactional
    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request) {

        Employee existingEmployee = repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                                "Department not found with id: "
                                        + request.getDepartmentId()
                        )
                );

        existingEmployee.setName(request.getName());
        existingEmployee.setSalary(request.getSalary());
        existingEmployee.setDepartment(department);

        Employee updatedEmployee = repository.save(existingEmployee);

        return toResponse(updatedEmployee);
    }

    public void deleteEmployee(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        repository.delete(employee);
    }

    public List<Employee> getEmployeesByName(String name) {
        return repository.findByName(name);
    }

    public List<Employee> getEmployeesBySalaryGreaterThan(int salary) {
        return repository.findBySalaryGreaterThan(salary);
    }

    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        return repository.findByDepartmentId(departmentId);
    }

    public List<Employee> getEmployeesByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    public List<Employee> getEmployeesSortedBySalaryDesc() {
        return repository.findAllByOrderBySalaryDesc();
    }

    public Page<Employee> getEmployees(Pageable pageable) {
        return repository.findAll(pageable);
    }

    private EmployeeResponse toResponse(Employee employee) {

        String departmentName = employee.getDepartment() != null
                ? employee.getDepartment().getName()
                : null;

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getSalary(),
                departmentName
        );
    }
}