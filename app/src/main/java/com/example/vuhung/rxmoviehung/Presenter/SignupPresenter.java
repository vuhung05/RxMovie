package com.example.vuhung.rxmoviehung.Presenter;

import android.util.Log;

import com.example.vuhung.rxmoviehung.API.API;
import com.example.vuhung.rxmoviehung.API.APIManager;
import com.example.vuhung.rxmoviehung.API.Response.Signup.ModelSignupResponse;
import com.example.vuhung.rxmoviehung.View.Signup.SignupView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.example.vuhung.rxmoviehung.Presenter.SigninPresenter.EMAIL_ADDRESS_PATTERN;

public class SignupPresenter {
    SignupView signupView;

    public SignupPresenter(SignupView signupView) {
        this.signupView = signupView;
    }
    public void onSignup(String fullName,String email, String password, String passwordConfirm,String gender, String birthday) {
        signupView.showLoading();
        if (email.isEmpty() || password.isEmpty() || fullName.isEmpty() || passwordConfirm.isEmpty())
            signupView.getMessageError("Vui lòng nhập đủ thông tin");
        else
        if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches())
            signupView.getMessageError("Địa chỉ Email không đúng định dạng");
        else if (password.length() < 6)
            signupView.getMessageError("Mật khẩu không được ít hơn 6 ký tự");
        else
        if (!password.equals(passwordConfirm))
            signupView.getMessageError("Mật khẩu nhập lại không chính xác");
        else {
            API servie = APIManager.getClient().create(API.class);
            Call<ModelSignupResponse> signupResponseCall = servie.onSignupCall(fullName, email, password, gender, birthday);
            signupResponseCall.enqueue(new Callback<ModelSignupResponse>() {
                @Override
                public void onResponse(Response<ModelSignupResponse> response, Retrofit retrofit) {
                    if (response.isSuccess()) {

                        if (response.body().getError()) {
                            signupView.getMessageError(response.body().getMessage());
                        } else {

                            signupView.getMessageSuccess("Đăng ký thành công");
                        }
                    }
                }
                @Override
                public void onFailure(Throwable t) {

                }
            });

        }
        signupView.hideLoading();

    }
}
