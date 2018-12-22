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

import io.reactivex.Observable;
import itsc.hackathon.shareapp.data.network.model.LoginRequest;
import itsc.hackathon.shareapp.data.network.model.LoginResponse;
import itsc.hackathon.shareapp.data.network.model.SignupRequest;
import itsc.hackathon.shareapp.data.network.model.SignupResponse;
import itsc.hackathon.shareapp.data.network.model.comment.Comment;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.data.network.model.topic.Topic;

/**
 * Created by janisharali on 27/01/17.
 */

public interface ApiHelper {

    ApiHeader getApiHeader();

    Observable<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    Observable<SignupResponse> doServerSignupCall(SignupRequest.ServerSignupRequest request);

    Observable<List<Post>> getPosts();

    Observable<List<Topic>> getTopics();

    Observable<List<Comment>> getPostComments(String postId);

//  Single<LogoutResponse> doLogoutApiCall();
}
