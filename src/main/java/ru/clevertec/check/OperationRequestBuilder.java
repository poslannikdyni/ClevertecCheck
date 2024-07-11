package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.to.OperationRequestTO;
import main.java.ru.clevertec.check.exception.CustomException;

import java.math.BigDecimal;

public class OperationRequestBuilder {
    public OperationRequestTO fromArray(String[] array) {
        var request = new OperationRequestTO();
        for (String arg : array) {
            parse(request, arg);
        }
        return request;
    }

    private void parse(OperationRequestTO request, String arg) {
        if (arg.contains("=")) {
            parseArgumentWithEquals(request, arg);
        } else if (arg.contains("-")) {
            parseArgumentWithMinus(request, arg);
        } else {
            throw new CustomException("BAD REQUEST", "Invalid argument : " + arg);
        }
    }

    private void parseArgumentWithEquals(OperationRequestTO request, String arg) {
        var tmp = arg.split("=");
        if (tmp.length != 2) throw new CustomException("BAD REQUEST", "Invalid argument : " + arg);

        try {
            switch (tmp[0]) {
                case "discountCard" -> request.setDiscountCardNumber(Integer.parseInt(tmp[1]));
                case "balanceDebitCard" -> request.setBalanceDebitCard(new BigDecimal(tmp[1]));
                case "saveToFile" -> request.setSaveByPath(tmp[1]);
                case "datasource.url" -> request.setDataSourceUrl(tmp[1]);
                case "datasource.username" -> request.setDataSourceUsername(tmp[1]);
                case "datasource.password" -> request.setDataSourcePassword(tmp[1]);
                default -> throw new CustomException("BAD REQUEST", "Unknown key : " + tmp[0]);
            }
        } catch (NumberFormatException e) {
            throw new CustomException("BAD REQUEST", "Invalid argument : " + arg);
        }
    }

    private void parseArgumentWithMinus(OperationRequestTO request, String arg) {
        var tmp = arg.split("-");
        if (tmp.length != 2) throw new CustomException("BAD REQUEST", "Unknown format ProductId-Quantity : " +arg);

        try {
            var id = Long.parseLong(tmp[0]);
            var quantity = Integer.parseInt(tmp[1]);

            var saveQuantity = request.getIdQuantity().getOrDefault(id, 0);
            saveQuantity = saveQuantity + quantity;

            request.getIdQuantity().put(id, saveQuantity);
        } catch (NumberFormatException e) {
            throw new CustomException("BAD REQUEST", "Product id and quantity must be number");
        }
    }
}
