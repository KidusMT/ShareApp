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

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import itsc.hackathon.shareapp.di.ActivityContext;
import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.about.AboutMvpPresenter;
import itsc.hackathon.shareapp.ui.about.AboutMvpView;
import itsc.hackathon.shareapp.ui.about.AboutPresenter;
import itsc.hackathon.shareapp.ui.detail.DetailAdapter;
import itsc.hackathon.shareapp.ui.detail.DetailPostMvpPresenter;
import itsc.hackathon.shareapp.ui.detail.DetailPostMvpView;
import itsc.hackathon.shareapp.ui.detail.DetailPostPresenter;
import itsc.hackathon.shareapp.ui.login.LoginMvpPresenter;
import itsc.hackathon.shareapp.ui.login.LoginMvpView;
import itsc.hackathon.shareapp.ui.login.LoginPresenter;
import itsc.hackathon.shareapp.ui.main.MainMvpPresenter;
import itsc.hackathon.shareapp.ui.main.MainMvpView;
import itsc.hackathon.shareapp.ui.main.MainPresenter;
import itsc.hackathon.shareapp.ui.notification.NotificationAdapter;
import itsc.hackathon.shareapp.ui.notification.NotificationMvpPresenter;
import itsc.hackathon.shareapp.ui.notification.NotificationMvpView;
import itsc.hackathon.shareapp.ui.notification.NotificationPresenter;
import itsc.hackathon.shareapp.ui.post.PostAdapter;
import itsc.hackathon.shareapp.ui.post.PostMvpPresenter;
import itsc.hackathon.shareapp.ui.post.PostMvpView;
import itsc.hackathon.shareapp.ui.post.PostPresenter;
import itsc.hackathon.shareapp.ui.signup.SignupMvpPresenter;
import itsc.hackathon.shareapp.ui.signup.SignupMvpView;
import itsc.hackathon.shareapp.ui.signup.SignupPresenter;
import itsc.hackathon.shareapp.ui.splash.SplashMvpPresenter;
import itsc.hackathon.shareapp.ui.splash.SplashMvpView;
import itsc.hackathon.shareapp.ui.splash.SplashPresenter;
import itsc.hackathon.shareapp.ui.topic.TopicAdapter;
import itsc.hackathon.shareapp.ui.topic.TopicMvpPresenter;
import itsc.hackathon.shareapp.ui.topic.TopicMvpView;
import itsc.hackathon.shareapp.ui.topic.TopicPresenter;
import itsc.hackathon.shareapp.utils.rx.AppSchedulerProvider;
import itsc.hackathon.shareapp.utils.rx.SchedulerProvider;

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
    SignupMvpPresenter<SignupMvpView> provideSignupPresenter(
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
    PostAdapter providePostAdapter() {
        return new PostAdapter(new ArrayList<>());
    }

    @Provides
    PostMvpPresenter<PostMvpView> providePostPresenter(
            PostPresenter<PostMvpView> presenter) {
        return presenter;
    }

    @Provides
    NotificationAdapter provideNotificationAdapter() {
        return new NotificationAdapter(new ArrayList<>());
    }

    @Provides
    NotificationMvpPresenter<NotificationMvpView> provideNotificationPresenter(
            NotificationPresenter<NotificationMvpView> presenter) {
        return presenter;
    }

    @Provides
    DetailAdapter provideDetailAdapter() {
        return new DetailAdapter(new ArrayList<>());
    }

    @Provides
    DetailPostMvpPresenter<DetailPostMvpView> provideDetailPresenter(
            DetailPostPresenter<DetailPostMvpView> presenter) {
        return presenter;
    }

    @Provides
    TopicAdapter provideTopicAdapter() {
        return new TopicAdapter(new ArrayList<>());
    }

    @Provides
    TopicMvpPresenter<TopicMvpView> provideTopicPresenter(
            TopicPresenter<TopicMvpView> presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
