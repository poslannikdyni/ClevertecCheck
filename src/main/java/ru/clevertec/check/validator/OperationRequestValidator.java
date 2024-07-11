package main.java.ru.clevertec.check.validator;

import main.java.ru.clevertec.check.to.OperationRequestTO;
import main.java.ru.clevertec.check.exception.CustomException;

import java.util.Collection;

public class OperationRequestValidator {
    public void validate(OperationRequestTO request) {
        if(request.getIdQuantity().size() == 0) throw new CustomException("BAD REQUEST", "Product id-quantity must not be empty");
        validateQuantity(request.getIdQuantity().values());

        if(request.hasBalanceDebitCard() == false) throw new CustomException("BAD REQUEST", "Balance for debit card must not be empty");
    }

    private void validateQuantity(Collection<Integer> values) {
        for (Integer value : values) {
            if(value < 0) throw new CustomException("BAD REQUEST", "Product quantity must be greater than zero");
        }
    }
}
