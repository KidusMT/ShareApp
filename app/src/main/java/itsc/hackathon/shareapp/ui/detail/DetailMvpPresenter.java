package itsc.hackathon.shareapp.ui.detail;


import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface DetailMvpPresenter<V extends DetailMvpView> extends MvpPresenter<V> {

    void onLogOutClicked();

    void loadPosts();
}
