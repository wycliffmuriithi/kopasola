package com.kopasolar.controllers.wrappers;

/**
 * Class name: UserPasswordWrapper
 * Creater: wgicheru
 * Date:1/27/2020
 */
public class UserPasswordWrapper {
    String email;
    int token;
    String password;
    String confirmpassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    @Override
    public String toString() {
        return "UserPasswordWrapper{" +
                "email='" + email + '\'' +
                ", token=" + token +
                ", password='" + password + '\'' +
                ", confirmpassword='" + confirmpassword + '\'' +
                '}';
    }
}
