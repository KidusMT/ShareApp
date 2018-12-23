package itsc.hackathon.shareapp.ui.subscription;


import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface SubscriptionMvpPresenter<V extends SubscriptionMvpView> extends MvpPresenter<V> {

    void loadPosts();
}
