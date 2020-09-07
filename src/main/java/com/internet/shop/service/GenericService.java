package com.internet.shop.service;

import java.util.List;

public interface GenericService<T, R> {
    T create(T item);

    T get(R id);

    List<T> getAll();

    T update(T item);

    boolean delete(R id);
}
