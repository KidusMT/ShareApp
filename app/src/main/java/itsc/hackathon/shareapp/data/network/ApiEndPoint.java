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

/**
 * Created by amitshekhar on 01/02/17.
 */

public final class ApiEndPoint {

    public final static String LOGIN = "users/login";
    public final static String SIGNUP = "users";
    public final static String POST = "posts";

    public final static String NOTIFICATION = "notifications";
    public final static String USERS = "users";

    public final static String MEASUREMENT_DELETE = "sensors/{sensor_id}/measurements/{measurement_id}";
    public final static String MEASUREMENT_LIST = "sensors/{sensor_id}/measurements";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
