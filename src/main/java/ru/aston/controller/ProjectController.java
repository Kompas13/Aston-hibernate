package ru.aston.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.aston.model.Employee;
import ru.aston.model.Project;
import ru.aston.service.MultitableServiceInterface;
import ru.aston.service.ServiceInterface;

import java.util.List;


@RestController

@RequestMapping("/api/projects")
public class ProjectController {

    private final ServiceInterface<Project> projectService;
    private final MultitableServiceInterface<Employee, Project, String> multitableService;

    @Autowired
    public ProjectController(ServiceInterface<Project> projectService, MultitableServiceInterface<Employee, Project, String> multitableService) {
        this.projectService = projectService;
        this.multitableService = multitableService;
    }

    @GetMapping()
    public List<Project> showAllProject() {
        return projectService.getAll();
    }

    @GetMapping("/{id}")
    public Project getProject(@PathVariable long id) {
        return projectService.get(id);
    }

    @GetMapping("/employee")
    public List<Employee> getEmployeeOfProject(@RequestParam String projectName) {
        return multitableService.getEmployeeOfProject(projectName);
    }

    @PostMapping()
    public Project createProject(@RequestBody Project project) {
        projectService.create(project);
        return project;
    }

    @PutMapping()
    public Project updateProject(@RequestBody Project project) {
        return projectService.update(project);
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable long id) {
        projectService.delete(id);
        return "Project with ID = " + id + " was deleted";
    }
}
