package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String password, String profileDescription) {
        User user = new User(email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().equals(password);
        } else return false;
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public void updateProfileDescription(String email, String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Описание не может быть пустым");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Нет такого пользователя..."));
        user.setProfileDescription(description);
        userRepository.update(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
