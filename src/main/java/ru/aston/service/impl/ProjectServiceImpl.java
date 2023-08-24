package ru.aston.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.dao.DaoInterface;
import ru.aston.exception_handling.NoContentException;
import ru.aston.exception_handling.NoSuchElementException;
import ru.aston.model.Project;
import ru.aston.service.ServiceInterface;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ProjectServiceImpl implements ServiceInterface<Project> {

    private final DaoInterface<Project> projectDao;

    @Autowired
    public ProjectServiceImpl(DaoInterface<Project> projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public List<Project> getAll() {
        List<Project> projectsList = new ArrayList<>(projectDao.getAll());
        if (projectsList.isEmpty()) {
            throw new NoContentException("Database is Empty");
        }
        return projectDao.getAll();
    }

    @Override
    public void create(Project project) throws NoContentException {
        if (project == null) {
            throw new NoContentException("Project is empty");
        } else {
            projectDao.create(project);
        }
    }

    @Override
    public Project update(Project project) throws NoSuchElementException {
        isPresentProject(project.getId());
        return projectDao.update(project);
    }

    @Override
    public Project get(long id) throws NoSuchElementException {
        isPresentProject(id);
        return projectDao.get(id);
    }

    @Override
    public void delete(long id) throws NoSuchElementException {
        isPresentProject(id);
        projectDao.delete(id);
    }

    private void isPresentProject(long id) throws NoSuchElementException {
        Optional<Project> project = Optional.ofNullable(projectDao.get(id));
        if (project.isEmpty()) {
            throw new NoSuchElementException("There is no project with ID = "
                    + id + " in Database");
        }
    }
}
