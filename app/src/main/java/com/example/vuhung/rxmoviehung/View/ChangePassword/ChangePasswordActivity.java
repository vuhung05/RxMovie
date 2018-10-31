package com.example.vuhung.rxmoviehung.View.ChangePassword;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vuhung.rxmoviehung.Presenter.ChangePasswordPresenter;
import com.example.vuhung.rxmoviehung.R;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordView{
    EditText edtEmail;
    Button btnSendLinkForMe, btnOk;
    ImageView imgClose;

    ProgressDialog dialog;
    ChangePasswordPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_password);

        edtEmail = findViewById(R.id.edt_email_cpw);
        btnSendLinkForMe = findViewById(R.id.btn_sen_link_for_me);
        imgClose = findViewById(R.id.img_close_cp);

        btnOk = findViewById(R.id.btn_ok);
        btnOk.setVisibility(View.INVISIBLE);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        presenter = new ChangePasswordPresenter(this);
        btnSendLinkForMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onChangePassword(edtEmail.getText().toString().trim());
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.hide();
    }

    @Override
    public void getMessageSuccess(String message) {
        btnOk.setVisibility(View.VISIBLE);
        btnSendLinkForMe.getBackground().setAlpha(70);
        Toast.makeText(ChangePasswordActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void getMessageError(String message) {
        Toast.makeText(ChangePasswordActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
