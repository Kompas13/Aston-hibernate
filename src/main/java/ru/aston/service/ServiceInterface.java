package ru.aston.service;

import java.util.List;

public interface ServiceInterface<T> {

    List<T> getAll();

    void create(T t);

    T update(T t);

    T get(long id);

    void delete(long id);
}
