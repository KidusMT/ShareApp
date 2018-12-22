package itsc.hackathon.shareapp.ui.topic;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.ui.base.BasePresenter;
import itsc.hackathon.shareapp.utils.CommonUtils;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

/**
 * Created by KidusMT.
 */

public class TopicPresenter<V extends TopicMvpView> extends BasePresenter<V>
        implements TopicMvpPresenter<V> {

    @Inject
    public TopicPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadTopics() {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().getTopics()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(topics -> {
                    if (!isViewAttached())
                        return;

                    getMvpView().showTopics(topics);

                }, throwable -> {

                    if (!isViewAttached())
                        return;

                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));

                }));
    }
}
