package com.example.tr1_takeaway.loginService;

public class LoginResponse {
    private static boolean loginBool;


    public static boolean isLoginBool() {
        return loginBool;
    }


    public void setBoolean(boolean bool) {
        this.loginBool = bool;
    }
}
