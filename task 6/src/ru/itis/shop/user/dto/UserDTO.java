package ru.itis.shop.user.dto;

public class UserDTO {

    private String email;
    private String name;
    private String profileDescription;

    public UserDTO(String email, String name, String profileDescription) {
        this.email = email;
        this.name = name;
        this.profileDescription = profileDescription;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getProfileDescription() {
        return profileDescription;
    }
}
