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

package itsc.hackathon.shareapp.di.component;

import dagger.Component;
import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.di.module.ActivityModule;
import itsc.hackathon.shareapp.ui.about.AboutFragment;
import itsc.hackathon.shareapp.ui.detail.DetailAdapter;
import itsc.hackathon.shareapp.ui.detail.DetailPostFragment;
import itsc.hackathon.shareapp.ui.login.LoginActivity;
import itsc.hackathon.shareapp.ui.main.MainActivity;
import itsc.hackathon.shareapp.ui.notification.NotificationAdapter;
import itsc.hackathon.shareapp.ui.notification.NotificationFragment;
import itsc.hackathon.shareapp.ui.post.PostAdapter;
import itsc.hackathon.shareapp.ui.post.PostFragment;
import itsc.hackathon.shareapp.ui.signup.SignupActivity;
import itsc.hackathon.shareapp.ui.splash.SplashActivity;
import itsc.hackathon.shareapp.ui.subscription.SubscriptionAdapter;
import itsc.hackathon.shareapp.ui.subscription.SubscriptionFragment;
import itsc.hackathon.shareapp.ui.topic.TopicAdapter;
import itsc.hackathon.shareapp.ui.topic.TopicFragment;

/**
 * Created by janisharali on 27/01/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SignupActivity activity);

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(SplashActivity activity);

    void inject(AboutFragment fragment);

    //subscription
    void inject(SubscriptionFragment fragment);
    void inject(SubscriptionAdapter adapter);

    // notification
    void inject(TopicFragment fragment);
    void inject(TopicAdapter adapter);

    // notification
    void inject(NotificationFragment fragment);
    void inject(NotificationAdapter adapter);

    // detail
    void inject(DetailPostFragment fragment);
    void inject(DetailAdapter adapter);

    // post
    void inject(PostFragment fragment);
    void inject(PostAdapter adapter);
}
