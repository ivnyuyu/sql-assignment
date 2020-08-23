package com.ivan.yuyuk.sqlassignment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO  {

    @NotBlank(message = "Поле 'Логин' обязательно")
    @Size(min = 3, max = 20, message = "Длина Логина должна быть больше 3 и меньше 20 символов")
    private String userName;
    @NotBlank(message = "Поле 'Пароль' обязательно")
    @Size(min = 3, max = 20, message = "Длина Пароля должна быть больше 3 и меньше 20 символов")
    private String password;

    public UserDTO() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
