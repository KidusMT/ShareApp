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

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by janisharali on 28/01/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }


//    @Override
//    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest
//                                                              request) {
//        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
//                .addHeaders(mApiHeader.getPublicApiHeader())
//                .addBodyParameter(request)
//                .build()
//                .getObjectSingle(LoginResponse.class);
//    }

//    @Override
//    public Single<LogoutResponse> doLogoutApiCall() {
//        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGOUT)
//                .addHeaders(mApiHeader.getProtectedApiHeader())
//                .build()
//                .getObjectSingle(LogoutResponse.class);
//    }

}

