package itsc.hackathon.shareapp.ui.detail;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.ui.base.BasePresenter;
import itsc.hackathon.shareapp.utils.CommonUtils;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

/**
 * Created by KidusMT.
 */

public class DetailPostPresenter<V extends DetailPostMvpView> extends BasePresenter<V>
        implements DetailPostMvpPresenter<V> {

    private static final String TAG = "QRScanPresenter";

    @Inject
    public DetailPostPresenter(DataManager dataManager,
                               SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLogOutClicked() {
        // removing the token when the user logout
        getDataManager().setUserAsLoggedOut();
    }

    @Override
    public void loadComments(String postId) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getPostComments(postId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(comments -> {
                    if (!isViewAttached())
                        return;

                    getMvpView().showComments(comments);

                }, throwable -> {

                    if (!isViewAttached())
                        return;

                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));

                }));
    }

}
