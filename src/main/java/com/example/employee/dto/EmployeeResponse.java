package com.example.employee.dto;

public class EmployeeResponse {

    private Long id;
    private String name;
    private int salary;
    private String department;

    public EmployeeResponse() {
    }

    public EmployeeResponse(
            Long id,
            String name,
            int salary,
            String department) {

        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }
}
