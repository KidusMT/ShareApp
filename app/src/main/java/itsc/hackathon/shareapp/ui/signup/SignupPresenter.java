package itsc.hackathon.shareapp.ui.signup;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.data.network.model.SignupRequest;
import itsc.hackathon.shareapp.ui.base.BasePresenter;
import itsc.hackathon.shareapp.ui.login.LoginMvpPresenter;
import itsc.hackathon.shareapp.ui.login.LoginMvpView;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

public class SignupPresenter<V extends SignupMvpView> extends BasePresenter<V>
        implements SignupMvpPresenter<V> {

    @Inject
    public SignupPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onServerSignupCLick(String fullName, String username, String email, String password) {
        if (fullName == null || fullName.isEmpty()) {
            getMvpView().onError(R.string.empty_fullname);
        }
        if (username == null || username.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
        }
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .doServerSignupCall(new SignupRequest.ServerSignupRequest(fullName, username, email, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(signupResponse -> {
                    if(!isViewAttached()){
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().openMainActivity();
                },throwable -> {
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void onLoginClick() {
        getMvpView().openLoginActivity();
    }

    @Override
    public void onSignInAsGuestClick() {
        getMvpView().openMainActivity();
    }

}
