package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.api.repository.ApiRepositoryInterface;
import main.java.ru.clevertec.check.converter.CheckConverter;
import main.java.ru.clevertec.check.entity.AbstractEntity;
import main.java.ru.clevertec.check.entity.DiscountCard;
import main.java.ru.clevertec.check.entity.Product;
import main.java.ru.clevertec.check.exception.CustomException;
import main.java.ru.clevertec.check.repository.InMemoryDiscountCardRepository;
import main.java.ru.clevertec.check.repository.InMemoryProductRepository;
import main.java.ru.clevertec.check.service.CheckService;
import main.java.ru.clevertec.check.to.OperationRequestTO;
import main.java.ru.clevertec.check.validator.OperationRequestValidator;

import java.math.BigDecimal;

import static main.java.ru.clevertec.check.util.Utility.readFile;
import static main.java.ru.clevertec.check.util.Utility.saveFile;

public class CheckRunnerFasade {
    @FunctionalInterface
    private interface CSVParser<T> {
        T parse(String source);
    }

    private static CSVParser<Product> productParser;
    private static CSVParser<DiscountCard> discountCardParser;

    public static String PRODUCT_PATH = "./src/main/resources/products.csv";
    public static String DISCOUNT_CARD_PATH = "./src/main/resources/discountCards.csv";
    public static float WHOLESALE_STANDARD_DISCOUNT = 10;

    public void processingRequest(OperationRequestTO request) {
        OperationRequestValidator requestValidator = new OperationRequestValidator();
        requestValidator.validate(request);

        var productRepository = new InMemoryProductRepository();
        read(PRODUCT_PATH, productRepository, productParser);

        var discountCardRepository = new InMemoryDiscountCardRepository();
        read(DISCOUNT_CARD_PATH, discountCardRepository, discountCardParser);

        var checkService = new CheckService();
        checkService.setWholesaleDiscount(WHOLESALE_STANDARD_DISCOUNT);
        checkService.setProductsRepository(productRepository);
        checkService.setDiscountCardRepository(discountCardRepository);

        var check = checkService.execute(request);

        saveFile(request.getSaveByPath(), new CheckConverter().checkToText(check, false));
        System.out.println(new CheckConverter().checkToText(check, true));
    }

    private static <T extends AbstractEntity> void read(String path, ApiRepositoryInterface<Long, T> repo, CSVParser<T> parser) {
        var file = readFile(path);
        var array = file.split(System.lineSeparator());
        for (int i = 1; i < array.length; i++) {
            repo.save(parser.parse(array[i]));
        }
    }

    static {
        productParser = source -> {
            var arr = source.split(";");
            if (arr.length != 5)
                throw new CustomException("INTERNAL SERVER ERROR", "The Product data should be in 5 columns");
            try {
                var isWholesale = arr[4].equals("+");
                return new Product(
                        Long.parseLong(arr[0]),
                        arr[1],
                        new BigDecimal(arr[2].replace(",", ".")),
                        Integer.parseInt(arr[3]),
                        isWholesale
                );
            } catch (NumberFormatException e) {
                throw new CustomException("INTERNAL SERVER ERROR", "Unknown Product format");
            }
        };

        discountCardParser = source -> {
            var arr = source.split(";");
            if (arr.length != 3)
                throw new CustomException("INTERNAL SERVER ERROR", "The DiscountCard data should be in 3 columns");
            try {
                return new DiscountCard(
                        Long.parseLong(arr[0]),
                        Integer.parseInt(arr[1]),
                        Float.parseFloat(arr[2])
                );
            } catch (NumberFormatException e) {
                throw new CustomException("INTERNAL SERVER ERROR", "Unknown DiscountCard format");
            }
        };
    }
}
