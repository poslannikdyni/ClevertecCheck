package ru.clevertec.check.api.repository;

import ru.clevertec.check.entity.DiscountCard;

public interface ApiDiscountCardRepository extends ApiRepositoryInterface<Long, DiscountCard> {
    DiscountCard getByNumber(int discountCardNumber);
}
