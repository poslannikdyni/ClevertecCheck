package ru.clevertec.check.repository;

import ru.clevertec.check.api.repository.ApiDiscountCardRepository;
import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.exception.CustomException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcDiscountCardRepository extends JdbcAbstractRepository<DiscountCard> implements ApiDiscountCardRepository {

    public JdbcDiscountCardRepository(Connection connection) {
        super(connection, rs -> {
            try {
                return new DiscountCard(
                        rs.getLong("id"),
                        rs.getInt("number"),
                        rs.getFloat("amount")
                );
            } catch (SQLException e) {
                throw new CustomException("INTERNAL SERVER ERROR", "Build DiscoutCard failed", e);
            }
        });
    }

    @Override
    public DiscountCard getByNumber(int discountCardNumber) {
        return (DiscountCard) getLogic("SELECT * FROM public.discount_card WHERE number = ?", discountCardNumber);
    }

    @Override
    public DiscountCard save(DiscountCard some) {
        var query = "INSERT INTO public.discount_card (number, amount) VALUES (?, ?)";
        try (var stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, some.getNumber());
            stmt.setShort(2, Float.valueOf(some.getDiscount()).shortValue());
            stmt.executeUpdate();
            return some;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving discount card. Method save", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return deleteLogic("DELETE FROM public.discount_card WHERE id = ?", id);
    }

    @Override
    public DiscountCard getById(Long id) {
        return (DiscountCard) getLogic("SELECT * FROM public.discount_card WHERE id = ?", id);
    }

    @Override
    public Collection<DiscountCard> getAll() {
        var result = new ArrayList<DiscountCard>();
        for (var obj : getAllLogic("SELECT * FROM public.discount_card")) {
            result.add((DiscountCard) obj);
        }
        return result;
    }
}
