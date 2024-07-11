package ru.clevertec.check.validator;

import ru.clevertec.check.to.OperationRequestTO;
import ru.clevertec.check.exception.CustomException;

import java.util.Collection;

public class OperationRequestValidator {
    public void validate(OperationRequestTO request) {
        if(request.getIdQuantity().size() == 0) throw new CustomException("BAD REQUEST", "Product id-quantity must not be empty");
        validateQuantity(request.getIdQuantity().values());

        if(request.hasBalanceDebitCard() == false) throw new CustomException("BAD REQUEST", "Balance for debit card must not be empty");

        checkSctringArgument(request.getSaveByPath(), "BAD REQUEST", "Command line argument [saveToFile] not specified");
        checkSctringArgument(request.getSaveByPath(), "INTERNAL SERVER ERROR", "Command line argument [datasource.url] not specified");
        checkSctringArgument(request.getSaveByPath(), "INTERNAL SERVER ERROR", "Command line argument [datasource.username] not specified");
        checkSctringArgument(request.getSaveByPath(), "INTERNAL SERVER ERROR", "Command line argument [datasource.password] not specified");
    }

    private void checkSctringArgument(String arg, String error, String advancedInfo) {
        if(arg == null || arg.isEmpty())
            throw new CustomException(error, advancedInfo);

    }

    private void validateQuantity(Collection<Integer> values) {
        for (Integer value : values) {
            if(value < 0) throw new CustomException("BAD REQUEST", "Product quantity must be greater than zero");
        }
    }
}
