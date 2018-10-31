package com.example.vuhung.rxmoviehung.Presenter;

import android.util.Log;

import com.example.vuhung.rxmoviehung.API.API;
import com.example.vuhung.rxmoviehung.API.APIManager;
import com.example.vuhung.rxmoviehung.API.Response.ModelChangePasswordResponse;
import com.example.vuhung.rxmoviehung.View.ChangePassword.ChangePasswordView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.example.vuhung.rxmoviehung.Presenter.SigninPresenter.EMAIL_ADDRESS_PATTERN;

public class ChangePasswordPresenter {
    ChangePasswordView changePasswordView;
    public ChangePasswordPresenter(ChangePasswordView changePasswordView) {
        this.changePasswordView = changePasswordView;
    }

    public void onChangePassword(String email){
        changePasswordView.showLoading();
        if (email.isEmpty()){
            changePasswordView.getMessageError("Vui lòng nhập email");
            changePasswordView.hideLoading();
        }else if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
            changePasswordView.getMessageError("Vui lòng nhập đúng định dạng email");
            changePasswordView.hideLoading();
        }else {
            API service = APIManager.getClient().create(API.class);
            Call<ModelChangePasswordResponse> changePasswordResponseCall = service.onChangePasswordCal(email);
            changePasswordResponseCall.enqueue(new Callback<ModelChangePasswordResponse>() {
                @Override
                public void onResponse(Response<ModelChangePasswordResponse> response, Retrofit retrofit) {
                    if (response.isSuccess()) {

                        if (response.body().getError()){
                            changePasswordView.hideLoading();
                            changePasswordView.getMessageError(response.body().getMessage());
                        }else {
                            changePasswordView.getMessageSuccess(response.body().getMessage());
                            changePasswordView.hideLoading();
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

}
