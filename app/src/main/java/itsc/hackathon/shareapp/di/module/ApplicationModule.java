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

package itsc.hackathon.shareapp.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import itsc.hackathon.shareapp.BuildConfig;
import itsc.hackathon.shareapp.data.AppDataManager;
import itsc.hackathon.shareapp.data.DataManager;
import itsc.hackathon.shareapp.data.network.ApiHeader;
import itsc.hackathon.shareapp.data.network.ApiHelper;
import itsc.hackathon.shareapp.data.network.AppApiHelper;
import itsc.hackathon.shareapp.data.prefs.AppPreferencesHelper;
import itsc.hackathon.shareapp.data.prefs.PreferencesHelper;
import itsc.hackathon.shareapp.di.ApiInfo;
import itsc.hackathon.shareapp.di.ApplicationContext;
import itsc.hackathon.shareapp.di.DatabaseInfo;
import itsc.hackathon.shareapp.di.PreferenceInfo;
import itsc.hackathon.shareapp.utils.AppConstants;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader provideApiHeader(PreferencesHelper preferencesHelper) {
        return new ApiHeader(preferencesHelper.getAccessToken());
    }

}
