package com.internet.shop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, V> {
    T create(T item);

    Optional<T> get(V id);

    List<T> getAll();

    T update(T item);

    boolean delete(V id);
}
