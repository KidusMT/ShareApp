package itsc.hackathon.shareapp.ui.create;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.MvpApp;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.ui.base.BasePresenter;
import itsc.hackathon.shareapp.utils.CommonUtils;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by KidusMT.
 */

public class CreatePresenter<V extends CreateMvpView> extends BasePresenter<V>
        implements CreateMvpPresenter<V> {

    @Inject
    public CreatePresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void composeQuestion(String title, String toWhom, String description,  List<Uri> fileUris, int lowerApi) throws URISyntaxException {
        getMvpView().showLoading();

        Map<String, RequestBody> map = new HashMap<>();
        if (!title.isEmpty())
            map.put("title", CommonUtils.toRequestBody(title));
        if (!toWhom.isEmpty())
            map.put("towhom", CommonUtils.toRequestBody(toWhom));
        if (!description.isEmpty())
            map.put("description", CommonUtils.toRequestBody(description));
//        if (!questionType.isEmpty())
//            map.put("questionTypeId", CommonUtils.toRequestBody(questionType));

        if (fileUris != null)
            if (lowerApi == 0)
                for (int i = 0; i < fileUris.size(); i++) {
                    File file = new File(fileUris.get(i).getLastPathSegment());
                    RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    map.put("file\"; filename=\"" + fileUris.get(i).getLastPathSegment().substring(fileUris.get(i).getLastPathSegment().lastIndexOf("/") + 1), fileBody);
                }
//            else
//                for (int i = 0; i < fileUris.size(); i++) {
//                    File file = new File(MvpApp.getContext(), fileUris.get(i));//FileUtils.getFile(MvpApp.getContext(), fileUris.get(i));
//                    RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    map.put("file\"; filename=\"" + fileUris.get(i).getLastPathSegment().substring(fileUris.get(i).getLastPathSegment().lastIndexOf("/") + 1), fileBody);
//                }

//        getCompositeDisposable().add(getDataManager().composeQuestion(map)//title, toWhom, description, questionType, parts)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(questionResponse -> {
//                    if (!isViewAttached())
//                        return;
                    getMvpView().hideLoading();
                    getMvpView().onError("Question Composed Successfully");
                    getMvpView().openQuestionListActivity();
//                }, throwable -> {
//
//                    if (!isViewAttached())
//                        return;
//
//                    getMvpView().hideLoading();
//                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));
//                }));
    }

    @Override
    public void loadUserGroups() {

    }
}
