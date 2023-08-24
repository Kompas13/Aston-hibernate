package ru.aston.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.dao.MultitableDao;
import ru.aston.exception_handling.NoAddedElementException;
import ru.aston.model.Employee;
import ru.aston.model.Project;
import ru.aston.service.MultitableServiceInterface;

import java.util.List;


@Transactional
@Service
public class MultitableServiceInterfaceImpl
        implements MultitableServiceInterface<Employee, Project, String> {

    private final MultitableDao<Employee, Project, String> multitableDao;

    @Autowired
    public MultitableServiceInterfaceImpl(MultitableDao<Employee, Project, String> multitableDao) {
        this.multitableDao = multitableDao;
    }

    public List<Project> getProject(String lastName) throws NoAddedElementException {
        checkedRequestParam(lastName);
        return multitableDao.getProjectOfEmployee(lastName);
    }

    @Override
    public List<Employee> getEmployeeOfPosition(String positionName) throws NoAddedElementException {
        checkedRequestParam(positionName);
        return multitableDao.getEmployeeOfPosition(positionName);
    }

    @Override
    public List<Employee> getEmployeeOfProject(String projectName) throws NoAddedElementException {
        checkedRequestParam(projectName);
        return multitableDao.getEmployeeOfProject(projectName);
    }

    private void checkedRequestParam(String param) throws NoAddedElementException {
        if (param == null) {
            throw new NoAddedElementException("Request is Empty");
        }

    }
}
