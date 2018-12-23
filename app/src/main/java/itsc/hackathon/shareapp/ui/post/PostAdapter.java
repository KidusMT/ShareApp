package itsc.hackathon.shareapp.ui.post;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.data.network.model.topic.Topic;
import itsc.hackathon.shareapp.ui.base.BaseViewHolder;

public class PostAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Post> posts;
    private CallBack mCallback;
//    private MeasurementCallback mMeasurementCallback;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_posts, viewGroup, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder sensorViewHolder, int i) {
        sensorViewHolder.onBind(i);
    }

    public void addItems(List<Post> sensors) {
        this.posts.clear();
        this.posts.addAll(sensors);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setCallback(CallBack callback) {
        mCallback = callback;
    }

    public class SensorViewHolder extends BaseViewHolder {

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

        @BindView(R.id.card_post_btn_vote_up)
        ImageView btnVoteUp;

        @BindView(R.id.card_post_btn_vote_down)
        ImageView btnVoteDown;

        Post post;

        public SensorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (posts != null) {

                post = posts.get(position);

                // title
                if (!TextUtils.isEmpty(post.getTitle()))
                    mPostTitle.setText(String.valueOf(post.getTitle()));

                // date
                if (!TextUtils.isEmpty(post.getCreatedAt()))
                    mPostDate.setText(DateTimeUtils.formatWithStyle(post.getCreatedAt(),
                            DateTimeStyle.MEDIUM));

                // description
                if (!TextUtils.isEmpty(post.getDescription()))
                    mPostDescription.setText(String.valueOf(post.getDescription()));


                if (!TextUtils.isEmpty(post.getFile()))
                    mPostAttachment.setVisibility(View.VISIBLE);
                else
                    mPostAttachment.setVisibility(View.GONE);

                Log.e("--->getTopics", String.valueOf(post.getTopics()));
                if (post.getTopics() != null) {

                    for (Topic measurement : post.getTopics()) {
                        TextView measurementValue = new TextView(itemView.getContext());
                        measurementValue.setTextColor(itemView.getResources().getColor(R.color.white));
                        measurementValue.setGravity(Gravity.CENTER);
                        measurementValue.setBackground(itemView.getResources().getDrawable(R.drawable.topic_corner_border_gray));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 5, 3, 5);
                        measurementValue.setLayoutParams(params);
                        measurementValue.setWidth(120);
                        measurementValue.setHeight(70);
                        // for limiting the number of character that can be displayed
                        measurementValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                        measurementValue.setMaxLines(1);
                        measurementValue.setEllipsize(TextUtils.TruncateAt.END);

                        if (measurementValue.getParent() != null)
                            ((ViewGroup) measurementValue.getParent()).removeView(measurementValue);
                        measurementValue.setText(measurement.getId());
//                        measurementValue.setOnClickListener(view -> mMeasurementCallback.onItemClicked(measurement));
                        topicContainer.addView(measurementValue);
                    }
                }
            } else {
                // todo find a better way of handling this condition
            }
            itemView.setOnClickListener(v -> mCallback.onItemClicked(posts.get(getAdapterPosition())));
        }

        @Override
        protected void clear() {
            mPostTitle.setText("");
            mPostDate.setText("");
            mPostDescription.setText("");
            mPostVoteCount.setText("");
        }
    }
}
