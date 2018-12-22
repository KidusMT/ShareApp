package itsc.hackathon.shareapp.ui.post;


import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface PostMvpPresenter<V extends PostMvpView> extends MvpPresenter<V> {

    void onLogOutClicked();

    void loadPosts();
}
