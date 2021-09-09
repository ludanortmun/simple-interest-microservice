package com.example.simpleinterestmicroservice.infrastructure;

import com.example.simpleinterestmicroservice.domain.Credit;
import com.example.simpleinterestmicroservice.domain.CreditRepository;
import com.example.simpleinterestmicroservice.domain.Payment;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreditSqlRepository implements CreditRepository {

    private final DataSource dataSource;

    public CreditSqlRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Credit credit) {
        insertCredit(credit);
        insertPayments(credit.getId(), credit.getPayments());
    }

    @Override
    public Optional<Credit> getBy(String id) {
        Credit credit = null;
        String SELECT_QUERY = "SELECT credits.id, " +
                "credits.amount, " +
                "credits.terms, " +
                "credits.rate, " +
                "credits.creation_date, " +
                "payments.amount, " +
                "payments.due_date " +
                "FROM credits " +
                "JOIN payments ON (credits.id = payments.parent_id) " +
                "WHERE credits.id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            credit = fromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException throwable) {
            throw new RuntimeException(throwable);
        }

        return Optional.ofNullable(credit);
    }

    private Credit fromResultSet(ResultSet resultSet) throws SQLException {
        Credit credit = null;
        while (resultSet.next()) {
            if (credit == null) {
                credit = new Credit()
                        .setId(resultSet.getString("credits.id"))
                        .setAmount(resultSet.getDouble("credits.amount"))
                        .setTerms(resultSet.getInt("credits.terms"))
                        .setRate(resultSet.getDouble("credits.rate"))
                        .setCreationDate(resultSet.getDate("credits.creation_date").toLocalDate())
                        .setPayments(new ArrayList<>());
            }

            Payment payment = new Payment()
                    .setAmount(resultSet.getDouble("payments.amount"))
                    .setDateDue(resultSet.getDate("payments.due_date").toLocalDate());
            credit.getPayments().add(payment);
        }

        return credit;
    }


    private void insertCredit(Credit credit) {
        try (Connection connection = dataSource.getConnection()) {
            String INSERT_QUERY = "INSERT INTO credits (id, amount, terms, rate, creation_date) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1, credit.getId());
            statement.setDouble(2, credit.getAmount());
            statement.setInt(3, credit.getTerms());
            statement.setDouble(4, credit.getRate());
            statement.setDate(5, Date.valueOf(credit.getCreationDate()));
            statement.executeUpdate();
        } catch (SQLException throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private void insertPayments(String creditId, List<Payment> payments) {
        try (Connection connection = dataSource.getConnection()) {
            String INSERT_QUERY = "INSERT INTO payments (parent_id, amount, due_date) VALUES " + buildTuplesFrom(payments);
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            for (int i = 0; i < payments.size(); i++) {
                statement.setString(1 + (3 * i), creditId);
                statement.setDouble(2 + (3 * i), payments.get(i).getAmount());
                statement.setDate(3 + (3 * i), Date.valueOf(payments.get(i).getDateDue()));
            }
            statement.executeUpdate();
        } catch (SQLException throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private String buildTuplesFrom(List<Payment> payments) {
        String[] tuples = payments.stream().map(p -> "(?, ?, ?)").collect(Collectors.toList()).toArray(new String[payments.size()]);
        return String.join(", ", tuples);
    }
}
