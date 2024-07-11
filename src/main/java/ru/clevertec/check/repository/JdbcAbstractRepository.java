package ru.clevertec.check.repository;

import ru.clevertec.check.api.repository.ApiRepositoryInterface;
import ru.clevertec.check.entity.AbstractEntity;
import ru.clevertec.check.exception.CustomException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcAbstractRepository<T extends AbstractEntity> implements ApiRepositoryInterface<Long, T> {
    protected interface Parser{
        Object parse(ResultSet rs);
    }

    protected final Connection connection;
    protected final Parser parser;

    protected JdbcAbstractRepository(Connection connection, Parser parser) {
        this.connection = connection;
        this.parser = parser;
    }

    protected Object getLogic(String query, long number) {
        try (var stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, number);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) return parser.parse(rs);
            }
        } catch (SQLException e) {
            throw new CustomException("INTERNAL SERVER ERROR", "Jdbc exception. Method getByNumber with number : " + number, e);
        }
        return null;
    }

    protected boolean deleteLogic(String query, Long id) {
        try (var stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new CustomException("INTERNAL SERVER ERROR", "Jdbc exception. Method delete with id : " + id, e);
        }
    }

    protected List<Object> getAllLogic(String query) {
        var result = new ArrayList<>();
        try (var stmt = connection.prepareStatement(query); var rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.add(parser.parse(rs));
            }
        } catch (SQLException e) {
            throw new CustomException("INTERNAL SERVER ERROR", "Jdbc exception. Method getAll", e);
        }
        return result;
    }
}
