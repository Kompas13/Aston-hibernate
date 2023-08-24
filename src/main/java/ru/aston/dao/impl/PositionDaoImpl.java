package ru.aston.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aston.dao.DaoInterface;
import ru.aston.model.Position;

import java.util.List;

import static ru.aston.utils.CopyPropertiesUtil.getNullPropertyNames;


@Repository
public class PositionDaoImpl implements DaoInterface<Position> {

    private final SessionFactory sessionFactory;

    @Autowired
    public PositionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Position> getAll() {
        return getSession().createQuery("from Position",
                Position.class).getResultList();
    }

    @Override
    public void create(Position position) {
        getSession().persist(position);
    }

    @Override
    public Position get(long id) {
        return getSession().get(Position.class, id);
    }

    @Override
    public void delete(long id) {
        Position position = get(id);
        getSession().remove(position);
    }

    public Position update(Position position) {
        Position pos = getSession().get(Position.class, position.getId());
        BeanUtils.copyProperties(position, pos, getNullPropertyNames(position));
        getSession().merge(pos);
        return pos;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
