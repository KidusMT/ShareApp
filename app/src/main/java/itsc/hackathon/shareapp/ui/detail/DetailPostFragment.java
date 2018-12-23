package itsc.hackathon.shareapp.ui.detail;

import android.hardware.Sensor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.comment.Comment;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.di.component.ActivityComponent;
import itsc.hackathon.shareapp.ui.base.BaseFragment;

import static itsc.hackathon.shareapp.utils.AppConstants.DETAIL_POST_KEY;

public class DetailPostFragment extends BaseFragment implements DetailPostMvpView, DetailAdapter.Callback {

    @Inject
    DetailPostMvpPresenter<DetailPostMvpView> mPresenter;

    @Inject
    DetailAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.detail_comment_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.detail_swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.tv_no_comments)
    TextView tvNoMeasurement;

    @BindView(R.id.card_post_user_profile)
    ImageView mPostUserProfile;

    @BindView(R.id.card_post_title)
    TextView mPostTitle;

    @BindView(R.id.card_post_date)
    TextView mPostDate;

    @BindView(R.id.card_post_attachment)
    ImageView mPostAttachment;

    @BindView(R.id.card_post_description)
    TextView mPostDescription;

    @BindView(R.id.topic_container)
    LinearLayout topicContainer;

    @BindView(R.id.card_post_points)
    TextView mPostPoints;

    @BindView(R.id.card_post_comments_count)
    TextView mPostCommentCount;

    @BindView(R.id.card_post_vote_count)
    TextView mPostVoteCount;

    @BindView(R.id.detail_comment_count)
    TextView mCommentCount;

    @BindView(R.id.card_post_btn_vote_up)
    ImageView btnVoteUp;

    @BindView(R.id.card_post_btn_vote_down)
    ImageView btnVoteDown;


    Post post;


    public static final String TAG = "DetailPostFragment";

    public static String parentFragment;

    public static DetailPostFragment newInstance(Post post, String fragmentPassed) {
        Bundle args = new Bundle();
        args.putSerializable(DETAIL_POST_KEY, post);
        DetailPostFragment fragment = new DetailPostFragment();
        fragment.setArguments(args);
        parentFragment = fragmentPassed;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mAdapter.setCallback(this);
//            mAdapter.setEditCallback(this);
//            mAdapter.setDeleteCallback(this);
        }

        if (getArguments() != null)
            post = (Post) getArguments().getSerializable(DETAIL_POST_KEY);

        setUp(view);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // todo check how other classes done it
            if (post != null)
                mPresenter.loadComments(post.getId());
            mSwipeRefreshLayout.setRefreshing(false);
            hideLoading();
        });

        return view;
    }

    @Override
    protected void setUp(View view) {
        loadPage(post);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick() {
        getBaseActivity().onFragmentDetached(TAG, parentFragment);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onItemClicked(Comment comment) {

    }

    @Override
    public void showComments(List<Comment> comments) {
        if (comments != null) {
            if (comments.size() > 0) {
                if (tvNoMeasurement != null && tvNoMeasurement.getVisibility() == View.VISIBLE)
                    tvNoMeasurement.setVisibility(View.GONE);
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.GONE)
                    mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.addItems(comments);
                mCommentCount.setText(String.valueOf(comments.size()) + " comments");
            } else {
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.VISIBLE)
                    mRecyclerView.setVisibility(View.GONE);
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.GONE) {
                    tvNoMeasurement.setVisibility(View.VISIBLE);
                    tvNoMeasurement.setText("No comments list found.");
                }
                mCommentCount.setVisibility(View.GONE);
            }
        }
        hideLoading();
    }

    @Override
    public void openDetailSensorActivity(Sensor sensor) {

    }

    @Override
    public void loadPage(Post post) {
        if (post != null) {
            // we can display id if the title is not being displayed...but it most probably works.
            toolbarTitle.setText((TextUtils.isEmpty(post.getTitle())) ? String.valueOf(post.getId()) : String.valueOf(post.getTitle()));

            // fetching the comments using the postId
            if (!TextUtils.isEmpty(post.getId()))
                mPresenter.loadComments(post.getId());

            showLoading();
            // todo tvNoMeasurements for the measurements and the whole sensor is different should be implemented
            tvNoMeasurement.setVisibility(View.GONE);
            // todo get back here and fix the comments
//            showComments(post.getId(), post.getMeasurements());

            if (!TextUtils.isEmpty(post.getCreatedAt()))
                mPostDate.setText(String.valueOf(DateTimeUtils.formatWithStyle(post.getCreatedAt(),
                        DateTimeStyle.MEDIUM)));

            if (!TextUtils.isEmpty(post.getTitle()))
                mPostTitle.setText(String.valueOf(post.getTitle()));

            if (!TextUtils.isEmpty(post.getFile()))
                mPostAttachment.setVisibility(View.VISIBLE);
            else
                mPostAttachment.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(post.getDescription()))
                mPostDescription.setText(String.valueOf(post.getDescription()));

//            if (!TextUtils.isEmpty(post.get()))
//                mPostVoteCount

        } else {// todo there has to be a way of expression the tvNoMeasurement in here in the else clause

        }
    }
}
