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

package itsc.hackathon.shareapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.data.network.model.topic.Topic;
import itsc.hackathon.shareapp.ui.base.BaseActivity;
import itsc.hackathon.shareapp.ui.custom.RoundedImageView;
import itsc.hackathon.shareapp.ui.detail.DetailPostFragment;
import itsc.hackathon.shareapp.ui.login.LoginActivity;
import itsc.hackathon.shareapp.ui.notification.NotificationFragment;
import itsc.hackathon.shareapp.ui.post.PostCommunicator;
import itsc.hackathon.shareapp.ui.post.PostFragment;
import itsc.hackathon.shareapp.ui.subscription.SubscriptionCommunicator;
import itsc.hackathon.shareapp.ui.subscription.SubscriptionFragment;
import itsc.hackathon.shareapp.ui.topic.TopicFragment;
import itsc.hackathon.shareapp.utils.CommonUtils;
import itsc.hackathon.shareapp.utils.ScreenUtils;

/**
 * Created by janisharali on 27/01/17.
 */

public class MainActivity extends BaseActivity implements MainMvpView, PostCommunicator, SubscriptionCommunicator {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_view)
    DrawerLayout mDrawer;

    @BindView(R.id.navigation_view)
    NavigationView nvDrawer;

    private TextView mNameTextView;
    private TextView mEmailTextView;
    private RoundedImageView mProfileImageView;

    private ActionBarDrawerToggle mDrawerToggle;

    public static String CURRENT_TAG = PostFragment.TAG;
    private Handler mHandler;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);

        setUp();

        // todo check the saved instance state before opening the PostFragment
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.flContent, PostFragment.newInstance(), PostFragment.TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawers();
            return;
        }

        if (getSupportFragmentManager().getFragments().size() > 1) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(getSupportFragmentManager()
                    .getFragments().get(getSupportFragmentManager().getFragments().size() - 1).getTag());

            // this is like popping out the top fragment on the fragment stack list
            if (fragment != null)
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commitNow();
            unlockDrawer();
        } else {
            PostFragment postFragment = (PostFragment) getSupportFragmentManager().findFragmentByTag(PostFragment.TAG);
            if (postFragment != null && postFragment.isVisible()) {
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                        .setNegativeButton(getString(R.string.yes), (dialog, which) -> {
                            mPresenter.onLogOutClicked();
                            finish();
                        })
                        .show();
            } else {
                // if the opened fragment is beside the postFragment which is the home fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.flContent, PostFragment.newInstance(), PostFragment.TAG)
                        .commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // by default the drawer has to be closed. Not opened
//        if (mDrawer != null)
//            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onFragmentAttached() {
    }

    private void setupDrawerContent(NavigationView navigationView) {
        View headerView = navigationView.getHeaderView(0);
        mEmailTextView = headerView.findViewById(R.id.tv_email);
        mNameTextView = headerView.findViewById(R.id.tv_name);

        loadNavHeader();

        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_post:
                fragmentClass = PostFragment.class;
                CURRENT_TAG = PostFragment.TAG;
                changeToolbarTitle("Posts");
                break;
            case R.id.nav_topic:
                fragmentClass = TopicFragment.class;
                CURRENT_TAG = TopicFragment.TAG;
                changeToolbarTitle("Topics");
                break;
            case R.id.nav_notification:
                fragmentClass = NotificationFragment.class;
                CURRENT_TAG = NotificationFragment.TAG;
                changeToolbarTitle("Notifications");
                break;
            case R.id.nav_subscription:
                fragmentClass = SubscriptionFragment.class;
                CURRENT_TAG = SubscriptionFragment.TAG;
                changeToolbarTitle("Subscriptions");
                break;
            case R.id.nav_profile:
//                fragmentClass = MapFragment.class;
//                CURRENT_TAG = MapFragment.TAG;
//                changeToolbarTitle("Profile");
                break;
            case R.id.nav_setting:
//                CommonUtils.toast("settings clicked");
//                CURRENT_TAG = PostFragment.TAG;
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Logout", (dialog, id) -> {
                            mPresenter.onLogOutClicked();
                        })
                        .setNegativeButton("Cancel", (dialog, id) -> {
                            dialog.dismiss();
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            default:
                fragmentClass = PostFragment.class;
        }

        try {
            if (fragmentClass != null)
                fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            mDrawer.closeDrawers();
            return;
        }

        Fragment finalFragment = fragment;
        Runnable mPendingRunnable = () -> {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (finalFragment != null)
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.flContent, finalFragment, CURRENT_TAG)
                        .commit();
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void loadNavHeader() {
        // showing dot next to notifications label
        nvDrawer.getMenu().getItem(2).setActionView(R.layout.menu_dot);
    }

    @Override
    public void onFragmentDetached(String tag, String parent) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commitNow();
            unlockDrawer();

            // todo find out if this is going to be removed or not
                if (TextUtils.equals(parent, PostFragment.TAG)) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.flContent, PostFragment.newInstance(), PostFragment.TAG)
                        .commit();
            }
        }
    }

    @Override
    public void lockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void openRegistrationSensor() {

    }

    @Override
    public void openDetailPost(Post sensor, String parentFragment) {
        lockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)//adding animation
                .replace(R.id.cl_root_view, DetailPostFragment.newInstance(sensor, parentFragment), DetailPostFragment.TAG)
                .commit();
    }

    @Override
    public void updateUserName(String currentUserName) {
        mNameTextView.setText(currentUserName);
    }

    @Override
    public void updateUserEmail(String currentUserEmail) {
        mEmailTextView.setText(currentUserEmail);
    }

    @Override
    public void updateUserProfilePic(String currentUserProfilePicUrl) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void setUp() {
        changeToolbarTitle("Posts");

//        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        setupDrawerContent(nvDrawer);

        mPresenter.onNavMenuCreated();

    }

    public void changeToolbarTitle(String title) {
        mToolbar.setTitle(String.valueOf(title));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimary));
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void closeNavigationDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void fabClicked() {
        mPresenter.onFabClicked();
    }

    @Override
    public void onItemClicked(Post post) {
        mPresenter.onPostItemClicked(post, PostFragment.TAG);
    }

    @Override
    public void onItemClicked(Topic topic) {

    }
}
