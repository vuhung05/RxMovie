package com.example.vuhung.rxmoviehung.View.Signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vuhung.rxmoviehung.Model.Film;
import com.example.vuhung.rxmoviehung.Presenter.SigninPresenter;
import com.example.vuhung.rxmoviehung.R;
import com.example.vuhung.rxmoviehung.View.ChangePassword.ChangePasswordActivity;
import com.example.vuhung.rxmoviehung.View.ListFilm.ListFilmActivity;
import com.example.vuhung.rxmoviehung.View.Signup.SignupActivity;
import com.example.vuhung.rxmoviehung.View.TrailerActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


public class SigninActivity extends AppCompatActivity implements SigninView{

    public static boolean isSignIn = false;
    static SigninActivity signinActivity;
    EditText edtEmail, edtPassword;
    Button btnSignin,btnForgotpass;
    TextView tvSignup;
    ImageView imgClose;
    ProgressDialog dialog;


    CallbackManager callbackManager;
    LoginButton loginButton;

    SigninPresenter signinPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().logOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_signin);

        init();
        signinActivity = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);// tắt xoay màn hình

        if (isSignIn){
            startActivity(new Intent(SigninActivity.this,ListFilmActivity.class));
            finish();
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage("loading...");

        signinPresenter = new SigninPresenter(this);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinPresenter.onSignin(edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim());
            }
        });
        btnForgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this,ChangePasswordActivity.class));
            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Film film;
                film = getIntent().getExtras().getParcelable("film");
                Intent intent = new Intent(SigninActivity.this,SignupActivity.class);
                intent.putExtra("film", film);
                startActivity(intent);
            }
        });

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_gender","user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signinPresenter.onSigninWithFb();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


    private void init() {
        edtEmail = (EditText) findViewById(R.id.edt_email_si);
        edtPassword = (EditText) findViewById(R.id.edt_password_si);
        btnSignin = (Button) findViewById(R.id.btn_signin);
        btnForgotpass = (Button) findViewById(R.id.btn_forgot_password);
        tvSignup = (TextView) findViewById(R.id.tv_signup_si);
        imgClose = findViewById(R.id.img_close_si);
        loginButton = findViewById(R.id.login_button);
    }
    public static SigninActivity getInstance(){
        return   signinActivity;
    }


    @Override
    public void showLoading() {
      dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void getMessageSuccess(String message) {
        isSignIn = true;
        Toast.makeText(SigninActivity.this,message,Toast.LENGTH_SHORT).show();
        Film film;
        film = getIntent().getExtras().getParcelable("film");
        Intent intent = new Intent(SigninActivity.this,TrailerActivity.class);
        intent.putExtra("film", film);
        startActivity(intent);
        finish();
    }

    @Override
    public void getMessageError(String message) {
        Toast.makeText(SigninActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
