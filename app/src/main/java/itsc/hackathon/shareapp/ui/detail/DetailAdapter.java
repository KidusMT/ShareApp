package itsc.hackathon.shareapp.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;

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

                if (comment.getCreator() != null)
                    mCommentTitle.setText(String.valueOf(comment.getCreator().getUsername()));

                if (!TextUtils.isEmpty(comment.getCreatedAt()))
                    mCommentDate.setText(DateTimeUtils.formatWithStyle(comment.getCreatedAt(),
                            DateTimeStyle.MEDIUM));

                if (!TextUtils.isEmpty(comment.getText()))
                    mCommentDescription.setText(String.valueOf(comment.getText()));

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
