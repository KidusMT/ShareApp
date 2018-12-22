/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package itsc.hackathon.shareapp.ui.main;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.ui.base.BasePresenter;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

/**
 * Created by janisharali on 27/01/17.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    private static final String TAG = "MainPresenter";

    @Inject
    public MainPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewInitialized() {
//        getCompositeDisposable().add(getDataManager()
//                .getAllQuestions()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<List<Question>>() {
//                    @Override
//                    public void accept(List<Question> questionList) throws Exception {
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        if (questionList != null) {
//                            getMvpView().refreshQuestionnaire(questionList);
//                        }
//                    }
//                }));
    }

    @Override
    public void onNavMenuCreated() {
        if (!isViewAttached()) {
            return;
        }

        final String currentUserName = getDataManager().getCurrentUserName();
        if (currentUserName != null && !currentUserName.isEmpty()) {
            getMvpView().updateUserName(currentUserName);
        }

        final String currentUserEmail = getDataManager().getCurrentUserEmail();
        if (currentUserEmail != null && !currentUserEmail.isEmpty()) {
            getMvpView().updateUserEmail(currentUserEmail);
        }

        final String profilePicUrl = getDataManager().getCurrentUserProfilePicUrl();
        if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
            getMvpView().updateUserProfilePic(profilePicUrl);
        }
    }

    @Override
    public void onLogOutClicked() {
        // removing the token when the user logout
        getDataManager().setUserAsLoggedOut();
        getMvpView().openLoginActivity();
    }

    @Override
    public void onFabClicked() {
        getMvpView().lockDrawer();
        getMvpView().openRegistrationSensor();
    }

    @Override
    public void onPostItemClicked(Post post, String parent) {
        getMvpView().lockDrawer();
        getMvpView().openDetailPost(post, parent);
    }

}
