package com.example.vuhung.rxmoviehung.View.Signup;

public interface SignupView {
    void showLoading();
    void hideLoading();
    void getMessageSuccess(String message);
    void getMessageError(String message);
}
