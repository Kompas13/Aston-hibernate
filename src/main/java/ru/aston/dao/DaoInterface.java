package ru.aston.dao;

import java.util.List;

public interface DaoInterface<T>{

    List<T> getAll();

    void create(T t);

    T get(long id);

    void delete(long id);

    T update(T t);
}
