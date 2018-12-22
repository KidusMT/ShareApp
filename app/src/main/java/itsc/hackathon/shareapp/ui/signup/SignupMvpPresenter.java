package itsc.hackathon.shareapp.ui.signup;

import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.base.MvpPresenter;

@PerActivity
public interface SignupMvpPresenter<V extends SignupMvpView> extends MvpPresenter<V> {
    void onServerSignupCLick(String fullName,String username,String email, String password);
    void onLoginClick();
    void onSignInAsGuestClick();
}
