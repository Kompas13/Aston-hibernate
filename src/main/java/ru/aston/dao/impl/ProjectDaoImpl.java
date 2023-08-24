package ru.aston.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aston.dao.DaoInterface;
import ru.aston.model.Project;

import java.util.List;

import static ru.aston.utils.CopyPropertiesUtil.getNullPropertyNames;


@Repository
public class ProjectDaoImpl implements DaoInterface<Project> {
    private final SessionFactory sessionFactory;

    @Autowired
    public ProjectDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Project> getAll() {
        return getSession().createQuery("from Project",
                Project.class).getResultList();
    }

    @Override
    public void create(Project project) {
        getSession().persist(project);
    }

    @Override
    public Project get(long id) {
        return getSession().get(Project.class, id);
    }

    @Override
    public void delete(long id) {
        Project project = get(id);
        getSession().remove(project);
    }

    public Project update(Project project) {
        Project proj = getSession().get(Project.class, project.getId());
        BeanUtils.copyProperties(project, proj, getNullPropertyNames(project));
        getSession().merge(proj);
        return proj;
    }

    private Session getSession (){
        return sessionFactory.getCurrentSession();
    }
}
