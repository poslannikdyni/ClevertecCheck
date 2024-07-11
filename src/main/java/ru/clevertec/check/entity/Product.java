package main.java.ru.clevertec.check.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Product extends AbstractEntity {
    private String description;
    private BigDecimal price;
    private int quantityStock;
    private boolean isWholesale;

    public Product(Long id, String description, BigDecimal price, int quantityStock, boolean isWholesale) {
        super(id);
        this.description = description;
        this.price = price;
        this.quantityStock = quantityStock;
        this.isWholesale = isWholesale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public boolean isWholesale() {
        return isWholesale;
    }

    public void setWholesale(boolean wholesale) {
        isWholesale = wholesale;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product that)) return false;
        if (!super.equals(o)) return false;

        return price.compareTo(that.price) == 0 &&
                quantityStock == that.quantityStock &&
                isWholesale == that.isWholesale &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                description,
                price,
                quantityStock,
                isWholesale);
    }
}
