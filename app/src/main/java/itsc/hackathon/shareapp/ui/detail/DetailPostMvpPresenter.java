package itsc.hackathon.shareapp.ui.detail;


import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface DetailPostMvpPresenter<V extends DetailPostMvpView> extends MvpPresenter<V> {

    void onLogOutClicked();

    void loadComments(String postId);
}
