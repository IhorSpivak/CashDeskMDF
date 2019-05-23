package com.example.cashdesk.main_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cashdesk.R;
import com.example.cashdesk.helper.MainHelper;
import com.example.cashdesk.helper.PreferenceHelper;
import com.example.cashdesk.models.LoginRequest;
import com.example.cashdesk.second_activity.SecondActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.tvTechnicalSupport)
    TextView tvTechnicalSupport;

    @BindView(R.id.btnEnter)
    Button btnEnter;

    @BindView(R.id.pb)
    ProgressBar pb;

    @BindView(R.id.edLogin)
    EditText edLogin;

    @BindView(R.id.edPassword)
    EditText edPassword;

    @BindView(R.id.ilLogin)
    TextInputLayout ilLogin;

    @BindView(R.id.ilPsssword)
    TextInputLayout ilPsssword;

    private MainContract.Presenter presenter;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);

        activity.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String user = PreferenceHelper.getInstance(this).getString(PreferenceHelper.Key.KEY_USER);
        String locale = PreferenceHelper.getInstance(this).getString(PreferenceHelper.Key.LOCALE);
        if (user != null && !user.equals("") && locale != null && !locale.equals("")) {
            MainHelper.setLocale(this, new Locale(locale.toLowerCase()));
            SecondActivity.startActivity(this,
                    PreferenceHelper.getInstance(this).getString(PreferenceHelper.Key.KEY_USER));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        pb.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        presenter = new MainPresenter(this);
    }

    @OnClick({R.id.tvTechnicalSupport})
    public void onTvTechnicalSupport() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+380 67 829 43 95"));
        startActivity(intent);
    }

    @OnClick(R.id.btnEnter)
    public void onEnterBtn() {
        if (validateLogin() && validatePassword()) {
            LoginRequest request = new LoginRequest();
            request.setUsername(edLogin.getText().toString());
            request.setPassword(edPassword.getText().toString());
            presenter.onEnterBtnClicked(request);
            pb.setVisibility(View.VISIBLE);
            btnEnter.setText("");
        }
    }

    @Override
    public void showErrorMessage() {
        pb.setVisibility(View.GONE);
        btnEnter.setText(R.string.enter);
        Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartSecondActivity(String authKey) {
        pb.setVisibility(View.GONE);
        btnEnter.setText(R.string.enter);
        SecondActivity.startActivity(this, authKey);
    }

    private boolean validateLogin() {
        String email = edLogin.getText().toString().trim();

        if (email.isEmpty()) {
            ilLogin.setError(getResources().getString(R.string.empty_field));
            requestFocus(edLogin);
            return false;
        } else {
            ilLogin.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (edPassword.getText().toString().trim().isEmpty()) {
            ilPsssword.setError(getResources().getString(R.string.empty_field));
            requestFocus(edPassword);
            return false;
        } else {
            ilPsssword.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showProgressBar() {
        pb.setVisibility(View.VISIBLE);
        btnEnter.setText("");
    }

    @Override
    public void hideProgressBar() {
        btnEnter.setText(R.string.enter);
        pb.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
