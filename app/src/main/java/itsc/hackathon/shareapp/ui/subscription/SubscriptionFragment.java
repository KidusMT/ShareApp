package itsc.hackathon.shareapp.ui.subscription;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.data.network.model.topic.Topic;
import itsc.hackathon.shareapp.di.component.ActivityComponent;
import itsc.hackathon.shareapp.ui.base.BaseFragment;
import itsc.hackathon.shareapp.ui.post.CallBack;
import itsc.hackathon.shareapp.ui.post.PostAdapter;
import itsc.hackathon.shareapp.ui.post.PostCommunicator;
import itsc.hackathon.shareapp.ui.post.PostMvpPresenter;
import itsc.hackathon.shareapp.ui.post.PostMvpView;

public class SubscriptionFragment extends BaseFragment implements SubscriptionMvpView, SubscriptionAdapter.Callback {//, SensorAdapter.MeasurementCallback

    @Inject
    SubscriptionMvpPresenter<SubscriptionMvpView> mPresenter;

    @Inject
    SubscriptionAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.subscription_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.subscription_swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.tv_no_subscription)
    TextView tvNoSensors;

    SubscriptionCommunicator communicator;

    public static final String TAG = "SubscriptionFragment";

    public static SubscriptionFragment newInstance() {
        Bundle args = new Bundle();
        SubscriptionFragment fragment = new SubscriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mAdapter.setCallback(this);
        }

        setUp(view);

        mPresenter.loadPosts();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadPosts();
            mSwipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (SubscriptionCommunicator) context;
    }


    @Override
    protected void setUp(View view) {
        setUpRecyclerView();
        mPresenter.loadPosts();
        if (getBaseActivity().getSupportActionBar() != null)
            getBaseActivity().getSupportActionBar().setTitle("Posts");
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showSubscriptions(List<Topic> topics) {
        if (topics != null) {
            if (topics.size() > 0) {
                if (tvNoSensors != null && tvNoSensors.getVisibility() == View.VISIBLE)
                    tvNoSensors.setVisibility(View.GONE);
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.GONE)
                    mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.addItems(topics);
            } else {
                if (tvNoSensors != null && tvNoSensors.getVisibility() == View.GONE) {
                    tvNoSensors.setVisibility(View.VISIBLE);
                    tvNoSensors.setText("No topics list found.");
                }
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.VISIBLE)
                    mRecyclerView.setVisibility(View.GONE);
            }
        }
        hideLoading();
    }

    @Override
    public void openDetailSensorActivity(Topic topics) {

    }

    @Override
    public void loadPage() {
        mPresenter.loadPosts();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        return true;
    }

    @Override
    public void onItemClicked(Topic topic) {
        communicator.onItemClicked(topic);
    }
}
