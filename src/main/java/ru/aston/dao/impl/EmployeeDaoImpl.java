package ru.aston.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aston.dao.DaoInterface;
import ru.aston.model.Employee;

import java.util.List;

import static ru.aston.utils.CopyPropertiesUtil.getNullPropertyNames;


@Repository
public class EmployeeDaoImpl implements DaoInterface<Employee> {

    private final SessionFactory sessionFactory;

    @Autowired
    public EmployeeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Employee> getAll() {
        return getSession().createQuery("select e from Employee e left join fetch e.position",
                Employee.class).getResultList();
    }

    @Override
    public void create(Employee employee) {
        getSession().persist(employee);
    }

    @Override
    public Employee get(long id) {
        return getSession().get(Employee.class, id);
    }

    @Override
    public void delete(long id) {
        Employee employee = get(id);
        getSession().remove(employee);
    }

    public Employee update(Employee employee) {
        Employee emp = getSession().get(Employee.class, employee.getId());
        BeanUtils.copyProperties(employee, emp, getNullPropertyNames(employee));
        getSession().merge(emp);
        return emp;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }




}
