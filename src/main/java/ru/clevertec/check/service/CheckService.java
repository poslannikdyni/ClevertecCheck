package main.java.ru.clevertec.check.service;

import main.java.ru.clevertec.check.exception.CustomException;
import main.java.ru.clevertec.check.api.service.ApiCheckService;
import main.java.ru.clevertec.check.repository.InMemoryDiscountCardRepository;
import main.java.ru.clevertec.check.repository.InMemoryProductRepository;
import main.java.ru.clevertec.check.entity.DiscountCard;
import main.java.ru.clevertec.check.entity.Product;
import main.java.ru.clevertec.check.to.CheckTO;
import main.java.ru.clevertec.check.to.OperationRequestTO;

import java.math.BigDecimal;
import java.util.Map;

public class CheckService implements ApiCheckService {
    private float wholesaleDiscount;
    private InMemoryProductRepository productsRepository;
    private InMemoryDiscountCardRepository discountCardRepository;

    public void setWholesaleDiscount(float wholesaleDiscount) {
        this.wholesaleDiscount = wholesaleDiscount;
    }

    public void setProductsRepository(InMemoryProductRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public void setDiscountCardRepository(InMemoryDiscountCardRepository discountCardRepository) {
        this.discountCardRepository = discountCardRepository;
    }

    @Override
    public CheckTO execute(OperationRequestTO request) {
        DiscountCard discountCard = null;
        if (request.hasDiscountCard()) {
            discountCard = discountCardRepository.getByNumber(request.getDiscountCardNumber());
            if (discountCard == null) throw new CustomException("BAD REQUEST", "Not find discount card with number: " + request.getDiscountCardNumber());
        }

        var check = new CheckTO();
        check.setDiscountCard(discountCard);

        for (var idQuantity : request.getIdQuantity().entrySet()) {
            var product = getProduct(idQuantity);
            var item = buildCheckItem(product, idQuantity.getValue(), discountCard);

            check.setTotalPrice(check.getTotalPrice().add(item.getPrice()));
            check.setTotalDiscount(check.getTotalDiscount().add(item.getDiscount()));

            var itemPriceWithDiscount = item.getPrice().subtract(item.getDiscount());
            check.setTotalPriceWithDiscount(check.getTotalPriceWithDiscount().add(itemPriceWithDiscount));

            check.getItems().add(item);
        }

        if (check.getTotalPrice().compareTo(request.getBalanceDebitCard()) > 0) {
            throw new CustomException("NOT ENOUGH MONEY", "Not enough balance. Check : " + check.getTotalPrice() + " Actual balance : " + request.getBalanceDebitCard());
        }

        return check;
    }

    private Product getProduct(Map.Entry<Long, Integer> idQuantity) {
        var product = productsRepository.getById(idQuantity.getKey());
        if (product == null) throw new CustomException("BAD REQUEST", "Not find product with id: " + idQuantity.getKey());

        var count = idQuantity.getValue();
        if (product.getQuantityStock() < count)
            throw new CustomException("BAD REQUEST",
                    "Not enough stock for product with id: " + idQuantity.getKey() +
                            " [" + product.getDescription() + ", max : " + product.getQuantityStock() + ", your request : "+idQuantity.getValue()+"]"

            );
        return product;
    }

    private CheckTO.CheckItem buildCheckItem(Product product, Integer count, DiscountCard discountCard) {
        BigDecimal price = BigDecimal.valueOf(count).multiply(product.getPrice());
        var discountDescription = getDiscountDescription(product, count, discountCard);
        BigDecimal discount = price.multiply(BigDecimal.valueOf(discountDescription.percent)).divide(BigDecimal.valueOf(100));
        return new CheckTO.CheckItem(product, count, price, discount, discountDescription.isWholesaleDiscount);
    }

    private record DiscountDescription(boolean isWholesaleDiscount, float percent){}
    private DiscountDescription getDiscountDescription(Product product, Integer count, DiscountCard discountCard) {
        if (product.isWholesale() && count >= 5) return new DiscountDescription(true, wholesaleDiscount);
        return discountCard != null ? new DiscountDescription(false, discountCard.getDiscount()) : new DiscountDescription(false, 0);
    }
}
