package itsc.hackathon.shareapp.ui.create;


import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface CreateMvpPresenter<V extends CreateMvpView> extends MvpPresenter<V> {

    void loadNotifications();
}
