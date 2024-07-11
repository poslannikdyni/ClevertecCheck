package ru.clevertec.check.repository;

import ru.clevertec.check.entity.Product;
import ru.clevertec.check.entity.Product;
import ru.clevertec.check.api.repository.ApiProductRepository;
import ru.clevertec.check.exception.CustomException;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcProductRepository extends JdbcAbstractRepository<Product> implements ApiProductRepository {

    public JdbcProductRepository(Connection connection) {
        super(connection, rs -> {
            try {
                return new Product(        
                        rs.getLong("id"),
                        rs.getString("description"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity_in_stock"),
                        rs.getBoolean("wholesale_product")
                );
            } catch (SQLException e) {
                throw new CustomException("INTERNAL SERVER ERROR", "Build DiscoutCard failed", e);
            }
        });
    }

    @Override
    public Product save(Product some) {
        var query = "INSERT INTO public.product (number, amount) VALUES (?, ?, ?, ?)";
        try (var stmt = connection.prepareStatement(query)) {
            stmt.setString(1, some.getDescription());
            stmt.setBigDecimal(2, some.getPrice());
            stmt.setInt(3, some.getQuantityStock());
            stmt.setBoolean(4, some.isWholesale());
            stmt.executeUpdate();
            return some;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving product. Method save", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return deleteLogic("DELETE FROM public.product WHERE id = ?", id);
    }

    @Override
    public Product getById(Long id) {
        return (Product) getLogic("SELECT * FROM public.product WHERE id = ?", id);
    }

    @Override
    public Collection<Product> getAll() {
        var result = new ArrayList<Product>();
        for (var obj : getAllLogic("SELECT * FROM public.product")) {
            result.add((Product) obj);
        }
        return result;
    }
}
