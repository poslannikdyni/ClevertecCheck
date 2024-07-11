package main.java.ru.clevertec.check.repository;

import main.java.ru.clevertec.check.api.repository.ApiRepositoryInterface;
import main.java.ru.clevertec.check.entity.AbstractEntity;

import java.util.*;

public class InMemoryAbstractRepository<T extends AbstractEntity> implements ApiRepositoryInterface<Long, T> {
    protected Map<Long, T> repo = new HashMap<>();

    @Override
    public T save(T some) {
        repo.put(some.getId(), some);
        return some;
    }

    @Override
    public boolean delete(Long id) {
        if(repo.containsKey(id)) {
            repo.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public T getById(Long id) {
        return repo.get(id);
    }

    @Override
    public Collection<T> getAll() {
        return new ArrayList<>(repo.values());
    }
}
