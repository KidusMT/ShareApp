package itsc.hackathon.shareapp.ui.topic;


import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface TopicMvpPresenter<V extends TopicMvpView> extends MvpPresenter<V> {

    void loadTopics();
}
