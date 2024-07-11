package main.java.ru.clevertec.check.repository;

import main.java.ru.clevertec.check.entity.Product;
import main.java.ru.clevertec.check.api.repository.ApiProductRepository;

public class InMemoryProductRepository extends InMemoryAbstractRepository<Product> implements ApiProductRepository {
}
