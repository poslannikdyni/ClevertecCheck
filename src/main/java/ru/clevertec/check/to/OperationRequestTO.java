package main.java.ru.clevertec.check.to;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OperationRequestTO {
    private String saveByPath;
    private String dataSourceUrl;
    private String dataSourceUsername;
    private String dataSourcePassword;
    private Integer discountCardNumber;
    private BigDecimal balanceDebitCard;
    private Map<Long, Integer> idQuantity = new HashMap<Long, Integer>();

    public String getSaveByPath() {
        return saveByPath;
    }

    public void setSaveByPath(String saveByPath) {
        this.saveByPath = saveByPath;
    }

    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    public void setDataSourceUrl(String dataSourceUrl) {
        this.dataSourceUrl = dataSourceUrl;
    }

    public String getDataSourceUsername() {
        return dataSourceUsername;
    }

    public void setDataSourceUsername(String dataSourceUsername) {
        this.dataSourceUsername = dataSourceUsername;
    }

    public String getDataSourcePassword() {
        return dataSourcePassword;
    }

    public void setDataSourcePassword(String dataSourcePassword) {
        this.dataSourcePassword = dataSourcePassword;
    }

    public Integer getDiscountCardNumber() {
        return discountCardNumber;
    }

    public void setDiscountCardNumber(Integer discountCardNumber) {
        this.discountCardNumber = discountCardNumber;
    }

    public BigDecimal getBalanceDebitCard() {
        return balanceDebitCard;
    }

    public void setBalanceDebitCard(BigDecimal balanceDebitCard) {
        this.balanceDebitCard = balanceDebitCard;
    }

    public Map<Long, Integer> getIdQuantity() {
        return idQuantity;
    }

    public boolean hasDiscountCard() {
        return discountCardNumber != null;
    }

    public boolean hasBalanceDebitCard() {
        return balanceDebitCard != null;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationRequestTO that)) return false;

        return saveByPath.equals(that.saveByPath) &&
                dataSourceUrl.equals(that.dataSourceUrl) &&
                dataSourceUsername.equals(that.dataSourceUsername) &&
                dataSourcePassword.equals(that.dataSourcePassword) &&
                discountCardNumber.compareTo(that.discountCardNumber) == 0 &&
                balanceDebitCard.compareTo(that.balanceDebitCard) == 0 &&
                Objects.equals(idQuantity, that.idQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saveByPath,
                dataSourceUrl,
                dataSourceUsername,
                dataSourcePassword,
                discountCardNumber,
                balanceDebitCard,
                idQuantity);
    }
}
