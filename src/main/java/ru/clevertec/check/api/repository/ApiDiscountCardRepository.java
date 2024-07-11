package main.java.ru.clevertec.check.api.repository;

import main.java.ru.clevertec.check.entity.DiscountCard;

public interface ApiDiscountCardRepository extends ApiRepositoryInterface<Long, DiscountCard> {
    DiscountCard getByNumber(int discountCardNumber);
}
