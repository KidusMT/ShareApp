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

package itsc.hackathon.shareapp.data.network;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import itsc.hackathon.shareapp.data.network.model.LoginRequest;
import itsc.hackathon.shareapp.data.network.model.LoginResponse;
import itsc.hackathon.shareapp.data.network.model.SignupRequest;
import itsc.hackathon.shareapp.data.network.model.SignupResponse;
import itsc.hackathon.shareapp.data.network.model.comment.Comment;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.data.network.model.topic.Topic;

/**
 * Created by janisharali on 28/01/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;
    private ApiCall mApiCall;

    @Inject
    public AppApiHelper(ApiHeader apiHeader, ApiCall apiCall) {
        mApiHeader = apiHeader;
        mApiCall = apiCall;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }


    @Override
    public Observable<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest
                                                                  request) {
//        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
//                .addHeaders(mApiHeader.getPublicApiHeader())
//                .addBodyParameter(request)
//                .build()
//                .getObjectSingle(LoginResponse.class);
        return mApiCall.login(request);
    }

    @Override
    public Observable<SignupResponse> doServerSignupCall(SignupRequest.ServerSignupRequest request) {
        return mApiCall.signup(request);
    }

    @Override
    public Observable<List<Post>> getPosts() {
        return mApiCall.getPosts();
    }

    @Override
    public Observable<List<Topic>> getTopics() {
        return mApiCall.getTopics();
    }

    @Override
    public Observable<List<Comment>> getPostComments(String postId, String filter) {
        return mApiCall.getPostComments(postId, filter);
    }

    @Override
    public Observable<List<Topic>> getSubscription(String userId) {
        return mApiCall.getSubscription(userId);
    }

    @Override
    public Observable<List<Topic>> getTopicsByPostId(String postId) {
        return mApiCall.getTopicsForPostId(postId);
    }
}

