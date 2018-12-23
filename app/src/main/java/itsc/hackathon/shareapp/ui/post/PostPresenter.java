package itsc.hackathon.shareapp.ui.post;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.ui.base.BasePresenter;
import itsc.hackathon.shareapp.utils.CommonUtils;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

/**
 * Created by KidusMT.
 */

public class PostPresenter<V extends PostMvpView> extends BasePresenter<V>
        implements PostMvpPresenter<V> {

    @Inject
    public PostPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLogOutClicked() {
        // removing the token when the user logout
        getDataManager().setUserAsLoggedOut();
//        getMvpView().openLoginActivity();
    }

    @Override
    public void loadPosts() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getPosts()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(posts -> {
                    if (!isViewAttached())
                        return;

//                    for (int i = 0; i < posts.size(); i++) {
                    for (Post post: posts) {

//                        int finalI = i;
                        getCompositeDisposable().add(getDataManager().getTopicsByPostId(post.getId())
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(topics -> {
                                    if (!isViewAttached())
                                        return;

                                    post.setTopics(topics);
//                                    posts.get(0).setTopics(topics);

                                }, throwable -> {

                                    if (!isViewAttached())
                                        return;

                                    getMvpView().hideLoading();
                                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));

                                }));
                    }

                    // replace with the loaded topics when finished the loop
                    getMvpView().showPosts(posts);

                }, throwable -> {

                    if (!isViewAttached())
                        return;

                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));

                }));
    }

    @Override
    public void loadTopics(List<Post> posts) {
        // commented it out because the loading logic is going to be called in previous method
//        getMvpView().showLoading();


        getMvpView().showPosts(posts);
    }

}
