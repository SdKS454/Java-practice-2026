package ru.itis.shop.user.api;

import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.dto.UserDTO;

import java.util.List;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "0": {
                System.exit(0);
            }
            break;
            case "3": {
                findById();
            }
            break;
            case "4": {
                updateProfileDescription();
            }
            break;
            case "5": {
                findAll();
            }
            break;
            case "6": {
                findAllByProfilDescription();
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить описание профиля пользователя по почте");
        System.out.println("5. Показать информацию о всех пользователях");
        System.out.println("6. Показать информацию о пользователях с заданным profileDescription");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите name:");
        String name = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(email,name, password, profileDescription);
    }


    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.err.println("Email или пароль не верны");
        }
    }

    private void findById() {
        System.out.println("Напишите id пользователя");
        Integer id = scanner.nextInt();
        userService.findById(id);
    }

    private void updateProfileDescription() {
        System.out.println("Введите почту пользователя, описание которого хотите обновить...");
        String email = scanner.nextLine();
        System.out.println("Введите новое описание профиля...");
        String profileDescription = scanner.nextLine();
        try{
            userService.updateProfileDescription(email, profileDescription);
            System.out.println("Описание обновлено...");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private void findAll() {
        List<UserDTO> users = userService.findAll();
        for(UserDTO user : users) {
            System.out.println(user.getName() + "|" + user.getEmail());
        }
    }

    private void findAllByProfilDescription() {
        System.out.println("Введите описание профиля...");
        String description = scanner.nextLine();
        List<UserDTO> users = userService.findAllByProfileDescription(description);
        if(users.isEmpty()) {
            System.err.println("Пользователей с таким описанием нет...");
        }
        for(UserDTO user : users) {
            System.out.println(user.getName() + "|" + user.getProfileDescription());
        }
    }
}
