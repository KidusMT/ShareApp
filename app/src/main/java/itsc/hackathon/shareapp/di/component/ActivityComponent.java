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

import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.di.module.ActivityModule;
import itsc.hackathon.shareapp.ui.about.AboutFragment;
import itsc.hackathon.shareapp.ui.feed.FeedActivity;
import itsc.hackathon.shareapp.ui.feed.blogs.BlogFragment;
import itsc.hackathon.shareapp.ui.feed.opensource.OpenSourceFragment;
import itsc.hackathon.shareapp.ui.login.LoginActivity;
import itsc.hackathon.shareapp.ui.main.MainActivity;
import itsc.hackathon.shareapp.ui.main.rating.RateUsDialog;
import itsc.hackathon.shareapp.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created by janisharali on 27/01/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(SplashActivity activity);

    void inject(FeedActivity activity);

    void inject(AboutFragment fragment);

    void inject(OpenSourceFragment fragment);

    void inject(BlogFragment fragment);

    void inject(RateUsDialog dialog);

}
