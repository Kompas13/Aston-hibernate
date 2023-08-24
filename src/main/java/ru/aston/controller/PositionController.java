package ru.aston.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.aston.model.Employee;
import ru.aston.model.Position;
import ru.aston.model.Project;
import ru.aston.service.MultitableServiceInterface;
import ru.aston.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private final ServiceInterface<Position> positionService;
    private final MultitableServiceInterface<Employee, Project, String> multitableService;

    @Autowired
    public PositionController(ServiceInterface<Position> positionService,
                              MultitableServiceInterface<Employee, Project, String> multitableService) {
        this.positionService = positionService;
        this.multitableService = multitableService;
    }

    @GetMapping()
    public List<Position> showAllPosition() {
        return positionService.getAll();
    }

    @GetMapping("/{id}")
    public Position getPosition(@PathVariable long id) {
        return positionService.get(id);
    }

    @GetMapping("/employee")
    public List<Employee> getEmployeeOfPosition(@RequestParam String positionName) {
        return multitableService.getEmployeeOfPosition(positionName);
    }

    @PostMapping()
    public Position createPosition(@RequestBody Position position) {
        positionService.create(position);
        return position;
    }

    @PutMapping()
    public Position updatePosition(@RequestBody Position position) {
        return positionService.update(position);
    }

    @DeleteMapping("/{id}")
    public String deletePosition(@PathVariable long id) {
        positionService.delete(id);
        return "Position with ID = " + id + " was deleted";
    }
}
