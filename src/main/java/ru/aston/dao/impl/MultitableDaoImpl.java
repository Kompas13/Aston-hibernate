package ru.aston.dao.impl;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aston.dao.MultitableDao;
import ru.aston.model.Employee;
import ru.aston.model.Project;

import java.util.List;


@Repository
public class MultitableDaoImpl implements MultitableDao<Employee, Project, String> {

    private final SessionFactory sessionFactory;

    @Autowired
    public MultitableDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Project> getProjectOfEmployee(String lastName) {
        Query<Project> query =
                getSession().createQuery(
                        "from Project p inner join p.employeeList el where el.lastName = :lastName",
                        Project.class);
        query.setParameter("lastName", lastName);

        return query.getResultList();
    }
    @Override
    public List<Employee> getEmployeeOfPosition(String positionName) {
        Query<Employee> query =
                getSession().createQuery(
                        "from Employee e inner join e.position p where p.positionName = :positionName",
                        Employee.class);
        query.setParameter("positionName", positionName);

        return query.getResultList();
    }
    @Override
    public List<Employee> getEmployeeOfProject(String projectName) {
        Query<Employee> query =
                getSession().createQuery(
                        "from Employee e inner join e.projectList pl where pl.projectName = :projectName",
                        Employee.class);
        query.setParameter("projectName", projectName);

        return query.getResultList();
    }
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}

