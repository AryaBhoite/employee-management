package com.example.employee.controller;

import com.example.employee.dto.EmployeeRequest;
import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.employee.dto.EmployeeResponse;
import com.example.employee.dto.EmployeeResponse;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse employee = service.createEmployee(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employee);
    }
    @GetMapping("/search")
    public List<Employee> searchEmployees(
            @RequestParam String name) {

        return service.getEmployeesByName(name);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(
            @PathVariable Long id) {

        return service.getEmployeeById(id);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return service.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
    @GetMapping("/search/salary")
    public List<Employee> searchEmployeesBySalary(
            @RequestParam int salary) {

        return service.getEmployeesBySalaryGreaterThan(salary);
    }
    @GetMapping("/search/department")
    public List<Employee> searchEmployeesByDepartment(
            @RequestParam Long departmentId) {

        return service.getEmployeesByDepartmentId(departmentId);
    }
    @GetMapping("/search/name")
    public List<Employee> searchEmployeesByNameContaining(
            @RequestParam String name) {

        return service.getEmployeesByNameContaining(name);
    }
    @GetMapping("/sorted/salary")
    public List<Employee> getEmployeesSortedBySalary() {
        return service.getEmployeesSortedBySalaryDesc();
    }
    @GetMapping("/page")
    public Page<Employee> getEmployees(Pageable pageable) {
        return service.getEmployees(pageable);
    }
}