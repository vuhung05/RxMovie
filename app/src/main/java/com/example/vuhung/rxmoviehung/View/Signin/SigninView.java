package com.example.vuhung.rxmoviehung.View.Signin;

public interface SigninView {
    void showLoading();
    void hideLoading();
    void getMessageSuccess(String message);
    void getMessageError(String message);
}
