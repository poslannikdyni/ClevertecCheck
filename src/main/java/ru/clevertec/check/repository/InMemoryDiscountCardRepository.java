package main.java.ru.clevertec.check.repository;

import main.java.ru.clevertec.check.entity.DiscountCard;
import main.java.ru.clevertec.check.api.repository.ApiDiscountCardRepository;

public class InMemoryDiscountCardRepository extends InMemoryAbstractRepository<DiscountCard> implements ApiDiscountCardRepository {
    @Override
    public DiscountCard getByNumber(int discountCardNumber) {
        return repo.values().stream().filter(i -> i.getNumber() == discountCardNumber).findFirst().orElse(null);
    }
}
