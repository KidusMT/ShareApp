package itsc.hackathon.shareapp.ui.notification;


import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface NotificationMvpPresenter<V extends NotificationMvpView> extends MvpPresenter<V> {

    void loadNotifications();
}
