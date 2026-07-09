package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.dto.UserDTO;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String name, String password, String profileDescription) {
        User user = new User(email, name, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public UserDTO findById(Integer id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID " + id + " не найден"));
    }

    public void updateProfileDescription(String email, String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Описание профиля не может быть пустым");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с email " + email + " не найден"));

        user.setProfileDescription(description);
        userRepository.update(user);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public List<UserDTO> findAllByProfileDescription(String description) {
        return userRepository.findAllByProfileDescription(description).stream()
                .map(this::toDto)
                .toList();
    }

    private UserDTO toDto(User user) {
        return new UserDTO(
                user.getEmail(),
                user.getName(),
                user.getProfileDescription()
        );
    }
}
