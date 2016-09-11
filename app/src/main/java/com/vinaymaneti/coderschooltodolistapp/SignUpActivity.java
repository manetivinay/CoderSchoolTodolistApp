package com.vinaymaneti.coderschooltodolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vinaymaneti on 9/10/16.
 */
public class SignUpActivity extends AppCompatActivity {

    public static final int REQUEST_REGISTER = 0;
    public static final int TIME_OUT = 3000;

//    @Bind(R.id.registerViewBackground)
//    View background;

    @Bind(R.id.registerUserNameInputLayout)
    TextInputLayout mRegisterUserNameInputLayout;

    @Bind(R.id.registerPasswordInputLayout)
    TextInputLayout mRegisterPasswordInputLayout;

    @Bind(R.id.registerEmailInputLayout)
    TextInputLayout mRegisterEmailInputLayout;

    @Bind(R.id.registerInputUserName)
    EditText mRegisterInputUserName;

    @Bind(R.id.registerInputEmail)
    EditText mRegisterInputEmail;

    @Bind(R.id.registerInputPassword)
    EditText mRegisterInputPassword;

    @Bind(R.id.btn_register)
    Button mBtnRegister;

    @Bind(R.id.linkAlreadyMember)
    TextView mLinkAlreadyMember;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        mLinkAlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIntent();
            }
        });

    }

    private void loginIntent() {
        Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private void register() {

        if (!validate()) {
            onRegisterFailure();
            return;
        }

        mBtnRegister.setEnabled(false);
        Util.showProgressDialog(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onRegistrationSuccess();
                Util.dismissProgessDialog();
            }
        }, TIME_OUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_REGISTER) {
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
        super.onBackPressed();
    }

    private void onRegistrationSuccess() {
        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
        loginIntent();
    }

    private void onRegisterFailure() {
        Toast.makeText(this, "Registration failed,please try agian", Toast.LENGTH_SHORT).show();
        mBtnRegister.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String userName = mRegisterInputUserName.getText().toString();
        String email = mRegisterInputEmail.getText().toString();
        String password = mRegisterInputPassword.getText().toString();

        if (userName.isEmpty() || userName.length() < 3 || userName.length() > 10) {
            mRegisterUserNameInputLayout.setError("between 3 to 10 alphanumeric characters");
            requestFocus(mRegisterInputEmail);
            valid = false;
        } else {
            mRegisterUserNameInputLayout.setErrorEnabled(false);
        }


        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mRegisterEmailInputLayout.setError("Please enter a valid email address");
            requestFocus(mRegisterInputEmail);
            valid = false;
        } else {
            mRegisterEmailInputLayout.setErrorEnabled(false);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mRegisterPasswordInputLayout.setError("betweeen 4 and 10 alphanumeric characters");
            requestFocus(mRegisterInputPassword);
            valid = false;
        } else {
            mRegisterPasswordInputLayout.setErrorEnabled(false);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }
}
