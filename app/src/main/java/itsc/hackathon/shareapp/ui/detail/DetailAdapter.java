package itsc.hackathon.shareapp.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.comment.Comment;
import itsc.hackathon.shareapp.ui.base.BaseViewHolder;

public class DetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Comment> comments;
    private Callback mCallback;

    public DetailAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_comments, viewGroup, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder sensorViewHolder, int i) {
        sensorViewHolder.onBind(i);
    }

    public void addItems(List<Comment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(Comment comment);
    }

    public class SensorViewHolder extends BaseViewHolder {

        @BindView(R.id.card_comment_user_profile)
        ImageView mCommentUserProfile;

        @BindView(R.id.card_comment_username)
        TextView mCommentTitle;

        @BindView(R.id.card_comment_date)
        TextView mCommentDate;

        @BindView(R.id.card_comment_description)
        TextView mCommentDescription;

        Comment comment;

        public SensorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (comments != null) {

                comment = comments.get(position);

                // id
//                mSensorId.setText((TextUtils.isEmpty(comment.getId())) ? comment.getName() : comment.getId());
//
//                // TODO check visibility first for every view before setting visibility
//                // date
//                if (!TextUtils.isEmpty(comment.getDateCreated())) {
//                    mSensorDate.setVisibility(View.VISIBLE);
//                    mSensorDate.setText(String.valueOf(DateTimeUtils.formatWithStyle(comment.getDateCreated(),
//                            DateTimeStyle.MEDIUM)));
//                } else {
//                    mSensorDate.setVisibility(View.GONE);
//                }
//
//                // owner
//                if (!TextUtils.isEmpty(comment.getOwner())) {
//                    ownerIcon.setVisibility(View.VISIBLE);
//                    mSensorOwner.setVisibility(View.VISIBLE);
//                    mSensorOwner.setText(String.valueOf(comment.getOwner()));
//                } else {
//                    ownerIcon.setVisibility(View.GONE);
//                    mSensorOwner.setVisibility(View.GONE);
//                }
//
//                // domain
//                if (!TextUtils.isEmpty(comment.getDomain())) {
//                    mSensorDomain.setVisibility(View.VISIBLE);
//                    mSensorDomain.setText(String.valueOf(comment.getDomain()));
//                } else {
//                    mSensorDomain.setVisibility(View.GONE);
//                }
//
//                measurementContainer.removeAllViews();
//
//                if (comment.getMeasurements() != null) {
//
//                    if (comment.getMeasurements().size() > 0) {
//                        measurementsTitle.setVisibility(View.VISIBLE);
//                    } else {
//                        measurementsTitle.setVisibility(View.GONE);
//                    }

//
//                    for (Measurement measurement : comment.getMeasurements()) {
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

            itemView.setOnClickListener(v -> mCallback.onItemClicked(comments.get(getAdapterPosition())));

        }

        @Override
        protected void clear() {
            mCommentTitle.setText("");
            mCommentDate.setText("");
            mCommentDescription.setText("");
        }
    }
}
