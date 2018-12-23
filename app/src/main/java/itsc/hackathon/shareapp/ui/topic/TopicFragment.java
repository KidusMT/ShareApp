package itsc.hackathon.shareapp.ui.topic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import itsc.hackathon.shareapp.ui.subscription.SubscriptionCommunicator;
import itsc.hackathon.shareapp.ui.subscription.SubscriptionFragment;

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
    TextView tvNoSensors;

    SubscriptionCommunicator communicator;

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
//            mAdapter.setCallback(this);
        }

        setUp(view);

        mPresenter.loadSubscription();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadSubscription();
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
        mPresenter.loadSubscription();
        if (getBaseActivity().getSupportActionBar() != null)
            getBaseActivity().getSupportActionBar().setTitle("Topic");
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getBaseActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
        mPresenter.loadSubscription();
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
//        communicator.onItemClicked(topic);
    }
}
