package itsc.hackathon.shareapp.ui.post;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.ui.base.BasePresenter;
import itsc.hackathon.shareapp.utils.CommonUtils;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

/**
 * Created by KidusMT.
 */

public class PostPresenter<V extends PostMvpView> extends BasePresenter<V>
        implements PostMvpPresenter<V> {

    private static final String TAG = "QRScanPresenter";

    @Inject
    public PostPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLogOutClicked() {
        // removing the token when the user logout
        getDataManager().setUserAsLoggedOut();
//        getMvpView().openLoginActivity();
    }

    @Override
    public void loadPosts() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getPosts()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(posts -> {
                    if (!isViewAttached())
                        return;

                    getMvpView().showPosts(posts);

                }, throwable -> {

                    if (!isViewAttached())
                        return;

                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));

                }));
    }

}
