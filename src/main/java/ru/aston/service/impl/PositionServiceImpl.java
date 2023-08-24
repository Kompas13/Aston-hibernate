package ru.aston.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.dao.DaoInterface;
import ru.aston.exception_handling.NoContentException;
import ru.aston.exception_handling.NoSuchElementException;
import ru.aston.model.Position;
import ru.aston.service.ServiceInterface;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PositionServiceImpl implements ServiceInterface<Position> {

    private final DaoInterface<Position> positionDao;

    @Autowired
    public PositionServiceImpl(DaoInterface<Position> positionDao) {
        this.positionDao = positionDao;
    }

    @Override
    public List<Position> getAll() {
        List<Position> positionList = new ArrayList<>(positionDao.getAll());
        if (positionList.isEmpty()) {
            throw new NoContentException("Database is Empty");
        }
        return positionDao.getAll();
    }

    @Override
    public void create(Position position) throws NoContentException {
        if (position == null) {
            throw new NoContentException("Position is empty");
        } else {
            positionDao.create(position);
        }
    }

    @Override
    public Position update(Position position) throws NoSuchElementException {
        isPresentPosition(position.getId());
        return positionDao.update(position);
    }

    @Override
    public Position get(long id) throws NoSuchElementException {
        isPresentPosition(id);
        return positionDao.get(id);
    }

    @Override
    public void delete(long id) throws NoSuchElementException {
        isPresentPosition(id);
        positionDao.delete(id);
    }

    private void isPresentPosition(long id) throws NoSuchElementException {
        Optional<Position> position = Optional.ofNullable(positionDao.get(id));
        if (position.isEmpty()) {
            throw new NoSuchElementException("There is no position with ID = "
                    + id + " in Database");
        }
    }
}
