package com.example.vuhung.rxmoviehung.Presenter;

import android.os.Bundle;
import android.util.Log;

import com.example.vuhung.rxmoviehung.API.API;
import com.example.vuhung.rxmoviehung.API.APIManager;
import com.example.vuhung.rxmoviehung.API.Response.Signin.ModelSigninResponse;
import com.example.vuhung.rxmoviehung.API.Response.Signin.ModelSigninWithFBResponse;
import com.example.vuhung.rxmoviehung.View.Signin.SigninView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SigninPresenter {
    SigninView signinView;
    String email, name, id, gender,avatar, facebookToken;
    String data;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public SigninPresenter(SigninView signinView) {
        this.signinView = signinView;
    }

    public void onSignin(String email, String password) {
        signinView.showLoading();
        if (email.isEmpty())
            signinView.getMessageError("Vui lòng nhập địa chỉ email");
        else if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches())
            signinView.getMessageError("Địa chỉ Email không đúng định dạng");
        else if (password.length() < 6)
            signinView.getMessageError("Mật khẩu không được ít hơn 6 ký tự");
        else {

            API service = APIManager.getClient().create(API.class);
            Call<ModelSigninResponse> call = service.onSigninCall(email,password);
            call.enqueue(new Callback<ModelSigninResponse>() {
                @Override
                public void onResponse(Response<ModelSigninResponse> response, Retrofit retrofit) {
                    if (response.body().getError()){
                        signinView.getMessageError(response.body().getMessage());

                    }else {
                        signinView.getMessageSuccess("Đăng nhập thành công!");
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
        signinView.hideLoading();
    }
    public void onSigninWithFb(){
        signinView.showLoading();
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                data = response.getJSONObject().toString();
                Log.d("abc","data facebook: "+response.getJSONObject().toString());

                Log.d("abcd",data.substring(data.indexOf("email")+8,data.indexOf("\",\"name")));
                email = data.substring(data.indexOf("email")+8,data.indexOf("\",\"name"));
                name = data.substring(data.indexOf("name")+7,data.indexOf("\",\"id"));
                id = data.substring(data.indexOf("id")+5,data.indexOf("\",\"gender"));
                gender = data.substring(data.indexOf("gender")+9,data.indexOf("\"}"));
                avatar = "https://graph.facebook.com/"+id+"/picture?type=large";
                facebookToken = AccessToken.getCurrentAccessToken().getToken();
                Log.d("abcd",email +"  "+ name +"  "+ id +"  " +gender + "  "+avatar + "   "+ facebookToken);

                API service = APIManager.getClient().create(API.class);
                Call<ModelSigninWithFBResponse> call = service.onSigninWithFbCall(email,name,id,gender,avatar,facebookToken);
                call.enqueue(new Callback<ModelSigninWithFBResponse>() {
                    @Override
                    public void onResponse(Response<ModelSigninWithFBResponse> response, Retrofit retrofit) {

                        if (response.body().getError())
                        {
                            signinView.getMessageError(response.body().getMessage());
                            signinView.hideLoading();
                        }else {
                            signinView.getMessageSuccess("Đăng nhập thành công!");
                            signinView.hideLoading();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,name,id,link,gender");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();


    }
}
