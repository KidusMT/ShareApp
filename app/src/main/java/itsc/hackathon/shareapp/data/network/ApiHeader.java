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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import itsc.hackathon.shareapp.di.ApiInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by janisharali on 27/01/17.
 */

@Singleton
public class ApiHeader {

    public static final String API_AUTH_TYPE = "API_AUTH_TYPE";
    public static final String PUBLIC_API = "PUBLIC_API";
    public static final String PROTECTED_API = "PROTECTED_API";

    public static final String HEADER_PARAM_API_KEY = "api_key";
    public static final String HEADER_PARAM_ACCESS_TOKEN = "Authorization";
    public static final String HEADER_PARAM_USER_ID = "user_id";

    private String mApiKey;
    private Long mUserId;
    private String mAccessToken;

    public ApiHeader(String mAccessToken) {
//        this.mApiKey = mApiKey;
//        this.mUserId = mUserId;
        this.mAccessToken = mAccessToken;
    }

//    public String getApiKey() {
//        return mApiKey;
//    }
//
//    public void setApiKey(String apiKey) {
//        mApiKey = apiKey;
//    }
//
//    public Long getUserId() {
//        return mUserId;
//    }
//
//    public void setUserId(Long userId) {
//        mUserId = userId;
//    }

    public String getAccessToken() {
        return "Bearer "+ mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }
}
