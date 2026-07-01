package ru.itis.shop.user.api;

import ru.itis.shop.user.application.UserService;

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
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить описание профиля пользователя по почте");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(email, password, profileDescription);
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
            System.out.println("Email или пароль не верны");
        }
    }

    private void findById() {
        System.out.println("Напишите id пользователя");
        String id = scanner.nextLine();
        userService.findById(id)
                .ifPresentOrElse(value -> {
                    System.out.println(value.getEmail());}, () -> {
                    System.out.println("Пользователя с таким id нет....");
                });
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
}
