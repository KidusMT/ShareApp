package itsc.hackathon.shareapp.ui.subscription;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.ui.base.BasePresenter;
import itsc.hackathon.shareapp.utils.CommonUtils;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

/**
 * Created by KidusMT.
 */

public class SubscriptionPresenter<V extends SubscriptionMvpView> extends BasePresenter<V>
        implements SubscriptionMvpPresenter<V> {

    @Inject
    public SubscriptionPresenter(DataManager dataManager,
                                 SchedulerProvider schedulerProvider,
                                 CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadPosts() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getSubscription(String.valueOf(getDataManager().getCurrentUserId()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(posts -> {
                    if (!isViewAttached())
                        return;

                    getMvpView().showSubscriptions(posts);

                }, throwable -> {

                    if (!isViewAttached())
                        return;

                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));

                }));
    }

}
