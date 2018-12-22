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

package itsc.hackathon.shareapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by janisharali on 28/01/17.
 */

public class ApiError {

    private int errorCode;

    @Expose
    @SerializedName("error")
    private String error;

    @Expose
    @SerializedName("description")
    private String description;

    public ApiError() {
    }

    public ApiError(int errorCode, String statusCode, String message) {
        this.errorCode = errorCode;
        this.error = statusCode;
        this.description = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatusCode() {
        return error;
    }

    public void setStatusCode(String statusCode) {
        this.error = statusCode;
    }

    public String getMessage() {
        return description;
    }

    public void setMessage(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ApiError apiError = (ApiError) object;

        if (errorCode != apiError.errorCode) return false;
        if (error != null ? !error.equals(apiError.error)
                : apiError.error != null)
            return false;
        return description != null ? description.equals(apiError.description) : apiError.description == null;

    }

    @Override
    public int hashCode() {
        int result = errorCode;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
