package itsc.hackathon.shareapp.ui.topic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.notification.Notification;
import itsc.hackathon.shareapp.data.network.model.topic.Topic;
import itsc.hackathon.shareapp.di.component.ActivityComponent;
import itsc.hackathon.shareapp.ui.base.BaseFragment;
import itsc.hackathon.shareapp.ui.notification.NotificationAdapter;
import itsc.hackathon.shareapp.ui.notification.NotificationMvpPresenter;
import itsc.hackathon.shareapp.ui.notification.NotificationMvpView;

public class TopicFragment extends BaseFragment implements TopicMvpView, TopicAdapter.Callback {

    @Inject
    TopicMvpPresenter<TopicMvpView> mPresenter;

    @Inject
    TopicAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.notification_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.notification_swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.tv_no_notification)
    TextView tvNoNotification;

    public static final String TAG = "TopicFragment";

    public static TopicFragment newInstance() {
        Bundle args = new Bundle();
        TopicFragment fragment = new TopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mAdapter.setCallback(this);
        }

        setUp(view);

        mPresenter.loadTopics();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadTopics();
            mSwipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    @Override
    protected void setUp(View view) {
        if (getBaseActivity().getSupportActionBar() != null)
            getBaseActivity().getSupportActionBar().setTitle("Notifications");
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showTopics(List<Topic> topics) {
        if (topics != null) {
            if (topics.size() > 0) {
                if (tvNoNotification != null && tvNoNotification.getVisibility() == View.VISIBLE)
                    tvNoNotification.setVisibility(View.GONE);
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.GONE)
                    mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.addItems(topics);
            } else {
                if (tvNoNotification != null && tvNoNotification.getVisibility() == View.GONE) {
                    tvNoNotification.setVisibility(View.VISIBLE);
                    tvNoNotification.setText("No topic list found.");
                }
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.VISIBLE)
                    mRecyclerView.setVisibility(View.GONE);
            }
        }
        hideLoading();
    }

    @Override
    public void onItemClicked(Topic topic) {

    }
}
