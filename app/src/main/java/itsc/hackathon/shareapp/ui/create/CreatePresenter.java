package itsc.hackathon.shareapp.ui.create;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.ui.base.BasePresenter;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

/**
 * Created by KidusMT.
 */

public class CreatePresenter<V extends CreateMvpView> extends BasePresenter<V>
        implements CreateMvpPresenter<V> {

    @Inject
    public CreatePresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadNotifications() {
        getMvpView().showLoading();

        // todo show mock notification data

        getMvpView().hideLoading();
//        getCompositeDisposable().add(getDataManager().getNotifications()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(notificationResponses -> {
//                    if (!isViewAttached())
//                        return;
//
//                    getMvpView().showNotifications(notificationResponses);
//
//                }, throwable -> {
//
//                    if (!isViewAttached())
//                        return;
//
//                    getMvpView().hideLoading();
//                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));
//
//                }));
    }
}
