package ru.aston.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.model.Employee;
import ru.aston.model.Project;
import ru.aston.service.MultitableServiceInterface;
import ru.aston.service.ServiceInterface;

import java.util.List;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final ServiceInterface<Employee> employeeService;
    private final MultitableServiceInterface<Employee, Project, String> multitableService;

    @Autowired
    public EmployeeController(ServiceInterface<Employee> employeeService,
                              MultitableServiceInterface<Employee, Project, String> multitableService) {
        this.employeeService = employeeService;
        this.multitableService = multitableService;
    }

    @GetMapping()
    public List<Employee> showAllEmployees() {
        return employeeService.getAll();
    }

    @GetMapping("/project")
    public List<Project> getProjectOfEmployee(@RequestParam String lastName) {
        return multitableService.getProject(lastName);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable long id) {
        return employeeService.get(id);
    }

    @PostMapping()
    public Employee createEmployee(@RequestBody Employee employee) {
        employeeService.create(employee);
        return employee;
    }

    @PutMapping()
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.update(employee);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable long id) {
        employeeService.delete(id);
        return "Employee with ID = " + id + " was deleted";
    }
}
