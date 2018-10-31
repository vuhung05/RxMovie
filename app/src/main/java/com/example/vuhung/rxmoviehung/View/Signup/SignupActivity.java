package com.example.vuhung.rxmoviehung.View.Signup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vuhung.rxmoviehung.Model.Film;
import com.example.vuhung.rxmoviehung.Presenter.SignupPresenter;
import com.example.vuhung.rxmoviehung.R;

import com.example.vuhung.rxmoviehung.View.TrailerActivity;
import static com.example.vuhung.rxmoviehung.View.Signin.SigninActivity.isSignIn;
import com.example.vuhung.rxmoviehung.View.Signin.SigninActivity;

public class SignupActivity extends AppCompatActivity implements SignupView {
    EditText edtFullName, edtEmail, edtPassword, edtConfirmPassword;
    Button btnSignup;
    ImageView imgClose;


    ProgressDialog dialog;

    SignupPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        init();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);// tắt xoay màn hình

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        presenter = new SignupPresenter(this);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSignup(edtFullName.getText().toString().trim(),
                        edtEmail.getText().toString().trim(),
                        edtPassword.getText().toString().trim(),
                        edtConfirmPassword.getText().toString().trim(),
                        "",
                        "");
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void init() {
        edtFullName = findViewById(R.id.edt_name_su);
        edtEmail = findViewById(R.id.edt_email_su);
        edtPassword = findViewById(R.id.edt_password_su);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password_su);
        btnSignup = findViewById(R.id.btn_signup);
        imgClose = findViewById(R.id.img_close_su);

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
        Toast.makeText(SignupActivity.this,message,Toast.LENGTH_SHORT).show();
        isSignIn = true;
        Film film;
        film = getIntent().getExtras().getParcelable("film");
        Intent intent = new Intent(SignupActivity.this,TrailerActivity.class);
        intent.putExtra("film", film);
        startActivity(intent);
        SigninActivity.getInstance().finish();
        finish();
    }

    @Override
    public void getMessageError(String message) {
        Toast.makeText(SignupActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
