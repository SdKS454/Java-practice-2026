package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    public UserFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(user.getId() + "|" +
                    user.getEmail() + "|" +
                    user.getPassword() + "|" +
                    user.getProfileDescription());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = reader.readLine()) != null) {
                if(line.isBlank()) continue;

                User user = parseUser(line);
                if(user.getId().equals(id)) {
                    return Optional.of(user);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return Optional.empty();
    }

    private User parseUser(String line) {
        String[] parts = line.split("\\|", 4);
        if(parts.length < 4) {
            throw new IllegalStateException("Некорректная строка в файле данных...");
        }
        return new User(parts[0], parts[1], parts[2], parts[3]);
    }
}
