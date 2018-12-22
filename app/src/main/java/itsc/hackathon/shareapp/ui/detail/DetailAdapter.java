package itsc.hackathon.shareapp.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.ui.base.BaseViewHolder;
import itsc.hackathon.shareapp.ui.post.CallBack;

public class DetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Post> posts;
    private CallBack mCallback;
//    private MeasurementCallback mMeasurementCallback;

    public DetailAdapter(List<Post> posts) {
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

//    public void setMeasurementCallback(MeasurementCallback callback) {
//        mMeasurementCallback = callback;
//}

//    public interface MeasurementCallback {
//        void onItemClicked(Measurement measurement);
//    }

    public class SensorViewHolder extends BaseViewHolder {

        @BindView(R.id.card_post_user_profile)
        ImageView mPostUserProfile;

        @BindView(R.id.card_post_title)
        TextView mPostTitle;

        @BindView(R.id.card_post_title)
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

                // id
//                mSensorId.setText((TextUtils.isEmpty(post.getId())) ? post.getName() : post.getId());
//
//                // TODO check visibility first for every view before setting visibility
//                // date
//                if (!TextUtils.isEmpty(post.getDateCreated())) {
//                    mSensorDate.setVisibility(View.VISIBLE);
//                    mSensorDate.setText(String.valueOf(DateTimeUtils.formatWithStyle(post.getDateCreated(),
//                            DateTimeStyle.MEDIUM)));
//                } else {
//                    mSensorDate.setVisibility(View.GONE);
//                }
//
//                // owner
//                if (!TextUtils.isEmpty(post.getOwner())) {
//                    ownerIcon.setVisibility(View.VISIBLE);
//                    mSensorOwner.setVisibility(View.VISIBLE);
//                    mSensorOwner.setText(String.valueOf(post.getOwner()));
//                } else {
//                    ownerIcon.setVisibility(View.GONE);
//                    mSensorOwner.setVisibility(View.GONE);
//                }
//
//                // domain
//                if (!TextUtils.isEmpty(post.getDomain())) {
//                    mSensorDomain.setVisibility(View.VISIBLE);
//                    mSensorDomain.setText(String.valueOf(post.getDomain()));
//                } else {
//                    mSensorDomain.setVisibility(View.GONE);
//                }
//
//                measurementContainer.removeAllViews();
//
//                if (post.getMeasurements() != null) {
//
//                    if (post.getMeasurements().size() > 0) {
//                        measurementsTitle.setVisibility(View.VISIBLE);
//                    } else {
//                        measurementsTitle.setVisibility(View.GONE);
//                    }

//
//                    for (Measurement measurement : post.getMeasurements()) {
//                        TextView measurementValue = new TextView(itemView.getContext());
//                        measurementValue.setTextColor(itemView.getResources().getColor(R.color.white));
//                        measurementValue.setGravity(Gravity.CENTER);
//                        measurementValue.setBackground(itemView.getResources().getDrawable(R.drawable.bg_curved_accent_color));
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                                ViewGroup.LayoutParams.WRAP_CONTENT,
//                                ViewGroup.LayoutParams.WRAP_CONTENT
//                        );
//                        params.setMargins(0, 5, 3, 5);
//                        measurementValue.setLayoutParams(params);
//                        measurementValue.setWidth(120);
//                        measurementValue.setHeight(70);
//                        // for limiting the number of character that can be displayed
//                        measurementValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
//                        measurementValue.setMaxLines(1);
//                        measurementValue.setEllipsize(TextUtils.TruncateAt.END);
//
//                        if (measurementValue.getParent() != null)
//                            ((ViewGroup) measurementValue.getParent()).removeView(measurementValue);
//                        measurementValue.setText(measurement.getId());
//                        measurementValue.setOnClickListener(view -> mMeasurementCallback.onItemClicked(measurement));
//                        measurementContainer.addView(measurementValue);
//                    }
//                }
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
