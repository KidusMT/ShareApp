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

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import itsc.hackathon.shareapp.data.network.model.BlogResponse;
import itsc.hackathon.shareapp.data.network.model.OpenSourceResponse;
import itsc.hackathon.shareapp.di.ActivityContext;
import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.about.AboutMvpPresenter;
import itsc.hackathon.shareapp.ui.about.AboutMvpView;
import itsc.hackathon.shareapp.ui.about.AboutPresenter;
import itsc.hackathon.shareapp.ui.feed.FeedMvpPresenter;
import itsc.hackathon.shareapp.ui.feed.FeedMvpView;
import itsc.hackathon.shareapp.ui.feed.FeedPagerAdapter;
import itsc.hackathon.shareapp.ui.feed.FeedPresenter;
import itsc.hackathon.shareapp.ui.feed.blogs.BlogAdapter;
import itsc.hackathon.shareapp.ui.feed.blogs.BlogMvpPresenter;
import itsc.hackathon.shareapp.ui.feed.blogs.BlogMvpView;
import itsc.hackathon.shareapp.ui.feed.blogs.BlogPresenter;
import itsc.hackathon.shareapp.ui.feed.opensource.OpenSourceAdapter;
import itsc.hackathon.shareapp.ui.feed.opensource.OpenSourceMvpPresenter;
import itsc.hackathon.shareapp.ui.feed.opensource.OpenSourceMvpView;
import itsc.hackathon.shareapp.ui.feed.opensource.OpenSourcePresenter;
import itsc.hackathon.shareapp.ui.login.LoginMvpPresenter;
import itsc.hackathon.shareapp.ui.login.LoginMvpView;
import itsc.hackathon.shareapp.ui.login.LoginPresenter;
import itsc.hackathon.shareapp.ui.main.MainMvpPresenter;
import itsc.hackathon.shareapp.ui.main.MainMvpView;
import itsc.hackathon.shareapp.ui.main.MainPresenter;
import itsc.hackathon.shareapp.ui.main.rating.RatingDialogMvpPresenter;
import itsc.hackathon.shareapp.ui.main.rating.RatingDialogMvpView;
import itsc.hackathon.shareapp.ui.main.rating.RatingDialogPresenter;
import itsc.hackathon.shareapp.ui.signup.SignupMvpView;
import itsc.hackathon.shareapp.ui.signup.SignupPresenter;
import itsc.hackathon.shareapp.ui.splash.SplashMvpPresenter;
import itsc.hackathon.shareapp.ui.splash.SplashMvpView;
import itsc.hackathon.shareapp.ui.splash.SplashPresenter;
import itsc.hackathon.shareapp.utils.rx.AppSchedulerProvider;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    AboutMvpPresenter<AboutMvpView> provideAboutPresenter(
            AboutPresenter<AboutMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SignupPresenter<SignupMvpView> provideSignupPresenter(
            SignupPresenter<SignupMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    RatingDialogMvpPresenter<RatingDialogMvpView> provideRateUsPresenter(
            RatingDialogPresenter<RatingDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedMvpPresenter<FeedMvpView> provideFeedPresenter(
            FeedPresenter<FeedMvpView> presenter) {
        return presenter;
    }

    @Provides
    OpenSourceMvpPresenter<OpenSourceMvpView> provideOpenSourcePresenter(
            OpenSourcePresenter<OpenSourceMvpView> presenter) {
        return presenter;
    }

    @Provides
    BlogMvpPresenter<BlogMvpView> provideBlogMvpPresenter(
            BlogPresenter<BlogMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedPagerAdapter provideFeedPagerAdapter(AppCompatActivity activity) {
        return new FeedPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    OpenSourceAdapter provideOpenSourceAdapter() {
        return new OpenSourceAdapter(new ArrayList<OpenSourceResponse.Repo>());
    }

    @Provides
    BlogAdapter provideBlogAdapter() {
        return new BlogAdapter(new ArrayList<BlogResponse.Blog>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
