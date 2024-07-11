package ru.clevertec.check;

import ru.clevertec.check.converter.CheckConverter;
import ru.clevertec.check.exception.CustomException;
import ru.clevertec.check.repository.JdbcDiscountCardRepository;
import ru.clevertec.check.repository.JdbcProductRepository;
import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.to.OperationRequestTO;
import ru.clevertec.check.validator.OperationRequestValidator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.clevertec.check.util.Utility.saveFile;

public class CheckRunnerFasade {
    private static float WHOLESALE_STANDARD_DISCOUNT = 10;
    private Connection connection;

    public void processingRequest(OperationRequestTO request) {
        OperationRequestValidator requestValidator = new OperationRequestValidator();
        requestValidator.validate(request);

        try {
            connection = DriverManager.getConnection(request.getDataSourceUrl(), request.getDataSourceUsername(), request.getDataSourcePassword());
        } catch (SQLException e) {
            throw new CustomException("INTERNAL SERVER ERROR", "No connection with database", e);
        }
        var productRepository = new JdbcProductRepository(connection);
        var discountCardRepository = new JdbcDiscountCardRepository(connection);

        var checkService = new CheckService();
        checkService.setWholesaleDiscount(WHOLESALE_STANDARD_DISCOUNT);
        checkService.setProductsRepository(productRepository);
        checkService.setDiscountCardRepository(discountCardRepository);

        var check = checkService.execute(request);

        saveFile(request.getSaveByPath(), new CheckConverter().checkToText(check, false));
        System.out.println(new CheckConverter().checkToText(check, true));
    }
}
