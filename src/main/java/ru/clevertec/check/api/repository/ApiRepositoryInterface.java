package ru.clevertec.check.api.repository;

import ru.clevertec.check.entity.AbstractEntity;

import java.util.Collection;

public interface ApiRepositoryInterface<I extends Number, T extends AbstractEntity> {
    T save(T some);

    boolean delete(I id);

    T getById(I id);

    Collection<T> getAll();
}
