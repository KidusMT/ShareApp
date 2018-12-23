package itsc.hackathon.shareapp.ui.notification;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.data.network.model.notification.Notification;
import itsc.hackathon.shareapp.ui.base.BasePresenter;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

/**
 * Created by KidusMT.
 */

public class NotificationPresenter<V extends NotificationMvpView> extends BasePresenter<V>
        implements NotificationMvpPresenter<V> {

    @Inject
    public NotificationPresenter(DataManager dataManager,
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

        Notification notification = new Notification(
                "New Content has been posted in Physics topic. Please check, as they might be useful for you too."
                , "Dec 23, 2018", "Physics");

        Notification notification1 = new Notification(
                "Material has been removed from under Machine Learning topic. Please check, as they might be useful for you too."
                , "Dec 2, 2018", "Physics");

        Notification notification2 = new Notification(
                "New Content has been posted in SQL topic. Please check, as they might be useful for you too."
                , "Jun 23, 2018", "SQL");

        Notification notification3 = new Notification(
                "New exam materials have been posted in under Physics topic. Please check, as they might be useful for you too."
                , "Feb 14, 2018", "Programming");

        Notification notification4 = new Notification(
                "New Content has been posted in Physics topic. Please check, as they might be useful for you too."
                , "Feb 2, 2018", "Machine Learning");

        Notification notification5 = new Notification(
                "New Content has been posted in Physics topic. Please check, as they might be useful for you too."
                , "Jan 24, 2018", "Physics");

        Notification notification6 = new Notification(
                "New Content has been posted in Physics topic. Please check, as they might be useful for you too."
                , "Jan 13, 2018", "SQL");

        Notification notification7 = new Notification(
                "New Content has been posted in Physics topic. Please check, as they might be useful for you too."
                , "Jan 3, 2018", "SQL");

        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        notifications.add(notification4);
        notifications.add(notification5);
        notifications.add(notification6);
        notifications.add(notification7);

        getMvpView().showNotifications(notifications);

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
