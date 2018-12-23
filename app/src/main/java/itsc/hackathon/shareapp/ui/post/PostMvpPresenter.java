package itsc.hackathon.shareapp.ui.post;


import java.util.List;

import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface PostMvpPresenter<V extends PostMvpView> extends MvpPresenter<V> {

    void onLogOutClicked();

    void loadPosts();

    void loadTopics(List<Post> posts);
}
