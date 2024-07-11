package main.java.ru.clevertec.check.to;

import main.java.ru.clevertec.check.entity.DiscountCard;
import main.java.ru.clevertec.check.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheckTO {
    public static class CheckItem {
        private Product product;
        private int count;
        private BigDecimal price;
        private BigDecimal discount;
        private boolean isWholesaleDiscount;

        public CheckItem(Product product, int count, BigDecimal price, BigDecimal discount, boolean isWholesaleDiscount) {
            this.product = product;
            this.count = count;
            this.price = price;
            this.discount = discount;
            this.isWholesaleDiscount = isWholesaleDiscount;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public BigDecimal getDiscount() {
            return discount;
        }

        public void setDiscount(BigDecimal discount) {
            this.discount = discount;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public boolean isWholesaleDiscount() {
            return isWholesaleDiscount;
        }

        public void setWholesaleDiscount(boolean wholesaleDiscount) {
            isWholesaleDiscount = wholesaleDiscount;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CheckItem that)) return false;

            return product.equals(that.product) &&
                    count == that.count &&
                    discount.compareTo(that.discount) == 0 &&
                    price.compareTo(that.price) == 0 &&
                    isWholesaleDiscount == that.isWholesaleDiscount;
        }

        @Override
        public int hashCode() {
            return Objects.hash(product,
                    count,
                    discount,
                    price,
                    isWholesaleDiscount);
        }
    }

    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal totalDiscount = BigDecimal.ZERO;
    private BigDecimal totalPriceWithDiscount = BigDecimal.ZERO;
    private DiscountCard discountCard;
    private List<CheckItem> items = new ArrayList<>();

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public void setTotalPriceWithDiscount(BigDecimal totalPriceWithDiscount) {
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    public List<CheckItem> getItems() {
        return items;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckTO that)) return false;

        return totalPrice.compareTo(that.totalPrice) == 0 &&
                totalDiscount.compareTo(that.totalDiscount) == 0 &&
                totalPriceWithDiscount.compareTo(that.totalPriceWithDiscount) == 0 &&
                Objects.equals(discountCard, that.discountCard) &&
                Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPrice,
                totalDiscount,
                totalPriceWithDiscount,
                discountCard,
                items);
    }
}
