package itsc.hackathon.shareapp.ui.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.ui.base.BaseActivity;
import itsc.hackathon.shareapp.ui.login.LoginActivity;
import itsc.hackathon.shareapp.ui.main.MainActivity;

public class SignupActivity extends BaseActivity implements SignupMvpView {

    @Inject
    SignupMvpPresenter<SignupMvpView> mPresenter;

    @BindView(R.id.et_username)
    EditText mUserNameEditText;

    @BindView(R.id.et_password)
    EditText mPasswordEditText;

    @BindView(R.id.et_email)
    EditText mEmailEditText;

    @BindView(R.id.full_name)
    EditText mFullNameEditText;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(SignupActivity.this);
    }

    @OnClick(R.id.btn_register)
    void onServerSignupCLick(){
//        Toast.makeText(this, "Hey", Toast.LENGTH_SHORT).show();
        mPresenter.onServerSignupCLick(mFullNameEditText.getText().toString(),
                mUserNameEditText.getText().toString(),
                mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString());
    }

    @OnClick(R.id.tv_login)
    void onLoginClick(View v){
        mPresenter.onLoginClick();
    }

    @OnClick(R.id.signin_as_guest)
    void onSignInAsGuestCLick(View v){
        mPresenter.onSignInAsGuestClick();
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(SignupActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openLoginActivity() {
//        Intent intent = new Intent()
//        LoginActivity.getStartIntent(SignupActivity.this);
        Intent intent = LoginActivity.getStartIntent( SignupActivity.this);
        startActivity(intent);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void onFragmentDetached(String tag, String parent) {

    }
}
