package com.vinaymaneti.coderschooltodolistapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.btn_login)
    AppCompatButton mLoginBtn;

    @Bind(R.id.emailInputLayout)
    TextInputLayout mEmailInputLayout;

    @Bind(R.id.passwordInputLayout)
    TextInputLayout mPasswordInputLayout;

    @Bind(R.id.inputEmail)
    EditText mInputEmail;

    @Bind(R.id.inputPassword)
    EditText mInputPassword;

    @Bind(R.id.linkSignUp)
    TextView mLinkSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        mLinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    private void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        mLoginBtn.setEnabled(false);

        Util.showProgressDialog(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onLoginSuccess();
                Util.dismissProgessDialog();
            }
        }, SignUpActivity.TIME_OUT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void onLoginSuccess() {
        Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show();
    }

    private void onLoginFailed() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();

        mLoginBtn.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = mInputEmail.getText().toString();
        String password = mInputPassword.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailInputLayout.setError("Enter a valid email address");
            requestFocus(mInputEmail);
            valid = false;
        } else {
            mEmailInputLayout.setErrorEnabled(false);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordInputLayout.setError("between 4 and 10 alphanumeric characters");
            requestFocus(mInputPassword);
            valid = false;
        } else {
            mPasswordInputLayout.setErrorEnabled(false);
        }

        return valid;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }
}
