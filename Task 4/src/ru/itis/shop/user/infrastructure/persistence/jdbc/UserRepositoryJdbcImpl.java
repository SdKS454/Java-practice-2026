package ru.itis.shop.user.infrastructure.persistence.jdbc;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl  implements UserRepository {
    @Override
    public void save(User user) {
        System.out.println("Сохраняю юзера...");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {
        System.out.println("Обновил пользователя...");
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:7689/postgres", "postgres", "IAPUKSGFD*)(&A<HGF*")) {

            // select * from account
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select * from account")) {
                    while (resultSet.next()) {
                        Integer id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");
                        String password = resultSet.getString("password");
                        String profileDescription = resultSet.getString("profiledescription");
                        User user = new User(id, name, email, password, profileDescription);
                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return users;
    }

    @Override
    public List<User> findAllByProfileDescription() {
        return List.of();
    }
}
