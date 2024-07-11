package main.java.ru.clevertec.check.converter;

import main.java.ru.clevertec.check.to.CheckTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckConverter {
    private static final String LS = System.lineSeparator();

    private static String priceFormat(BigDecimal price, String currency){
        return String.format("%.2f%s", price, currency);
    }

    public String checkToText(CheckTO check, boolean addAdditionalInfo) {
        StringBuilder sb = new StringBuilder();
        appendDateTime(sb);
        appendProduct(sb, check, addAdditionalInfo);
        appendDiscountCard(sb, check);
        appendTotal(sb, check);
        return sb.toString();
    }

    private void appendDateTime(StringBuilder sb) {
        sb.append("Date;Time");
        sb.append(LS);
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm:ss");
        sb.append(LocalDateTime.now().format(formatter));
        sb.append(LS);
        sb.append(LS);
    }

    private void appendProduct(StringBuilder sb, CheckTO check, boolean addAdditionalInfo) {
        sb.append("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL");
        sb.append(LS);

        for (CheckTO.CheckItem item : check.getItems()) {
            sb.append(item.getCount());
            sb.append(";");
            sb.append(item.getProduct().getDescription());
            sb.append(";");
            sb.append(priceFormat(item.getProduct().getPrice(), "$"));
            sb.append(";");
            sb.append(priceFormat(item.getDiscount(), "$"));
            sb.append(";");
            sb.append(priceFormat(item.getPrice(), "$"));
            if(addAdditionalInfo) {
                sb.append(" --- ");
                if(item.isWholesaleDiscount())
                    sb.append("wholesale discount on this position");
                else if(item.getDiscount().compareTo(BigDecimal.ZERO) > 0)
                    sb.append("personal discount on this position");
                else if(item.getDiscount().compareTo(BigDecimal.ZERO) == 0)
                    sb.append("no any discount");
            }
            sb.append(LS);
        }

        sb.append(LS);
    }

    private void appendDiscountCard(StringBuilder sb, CheckTO check) {
        if(check.getDiscountCard() != null) {
            sb.append("DISCOUNT CARD;DISCOUNT PERCENTAGE");
            sb.append(LS);
            sb.append(check.getDiscountCard().getNumber());
            sb.append(";");
            sb.append(percentFormat(check.getDiscountCard().getDiscount()));
            sb.append("%");
            sb.append(LS);
            sb.append(LS);
        }
    }

    private String percentFormat(Float value) {
        if(value.intValue() == value) return Integer.valueOf(value.intValue()).toString();
        return value.toString();
    }

    private void appendTotal(StringBuilder sb, CheckTO check) {
        sb.append("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT");
        sb.append(LS);
        sb.append(String.format("%s;", priceFormat(check.getTotalPrice(), "$")));
        sb.append(String.format("%s;", priceFormat(check.getTotalDiscount(), "$")));
        sb.append(String.format("%s%s", priceFormat(check.getTotalPriceWithDiscount(), "$"), LS));
        sb.append(LS);
    }
}
