package main.java.ru.clevertec.check.entity;

import java.util.Objects;

public class DiscountCard extends AbstractEntity {
    private int number;
    private float discount;

    public DiscountCard(Long id, int number, float discount) {
        super(id);
        this.number = number;
        this.discount = discount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountCard that)) return false;
        if (!super.equals(o)) return false;

        return number == that.number &&
                Float.compare(discount, that.discount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                number,
                discount);
    }
}