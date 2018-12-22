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

package itsc.hackathon.shareapp.data;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import itsc.hackathon.shareapp.data.network.ApiHeader;
import itsc.hackathon.shareapp.data.network.ApiHelper;
import itsc.hackathon.shareapp.data.network.model.LoginRequest;
import itsc.hackathon.shareapp.data.network.model.LoginResponse;
import itsc.hackathon.shareapp.data.network.model.SignupRequest;
import itsc.hackathon.shareapp.data.network.model.SignupResponse;
import itsc.hackathon.shareapp.data.network.model.comment.Comment;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.data.network.model.topic.Topic;
import itsc.hackathon.shareapp.data.prefs.PreferencesHelper;

/**
 * Created by janisharali on 27/01/17.
 */

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

//    @Override
//    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest
//                                                              request) {
//        return mApiHelper.doGoogleLoginApiCall(request);
//    }
//
//    @Override
//    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest
//                                                                request) {
//        return mApiHelper.doFacebookLoginApiCall(request);
//    }
//
//    @Override
//    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest
//                                                              request) {
//        return mApiHelper.doServerLoginApiCall(request);
//    }
//
//    @Override
//    public Single<LogoutResponse> doLogoutApiCall() {
//        return mApiHelper.doLogoutApiCall();
//    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public void updateUserInfo(String accessToken, LoggedInMode loggedInMode) {
        setAccessToken(accessToken);
        updateApiHeader(accessToken);
        setCurrentUserLoggedInMode(loggedInMode);
    }


    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(null, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT);
    }

//    @Override
//    public Single<BlogResponse> getBlogApiCall() {
//        return mApiHelper.getBlogApiCall();
//    }
//
//    @Override
//    public Single<OpenSourceResponse> getOpenSourceApiCall() {
//        return mApiHelper.getOpenSourceApiCall();
//    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Observable<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public Observable<SignupResponse> doServerSignupCall(SignupRequest.ServerSignupRequest request) {
        return mApiHelper.doServerSignupCall(request);
    }

    @Override
    public Observable<List<Post>> getPosts() {
        return mApiHelper.getPosts();
    }

    @Override
    public Observable<List<Topic>> getTopics() {
        return mApiHelper.getTopics();
    }

    @Override
    public Observable<List<Comment>> getPostComments(String postId) {
        return mApiHelper.getPostComments(postId);
    }

    @Override
    public void updateApiHeader(String accessToken) {
        mApiHelper.getApiHeader().setAccessToken(accessToken);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().setAccessToken(accessToken);
    }

    @Override
    public void updateUserToken(String accessToken) {
        setAccessToken(accessToken);

    }

}
