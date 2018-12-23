package itsc.hackathon.shareapp.ui.subscription;

import android.support.annotation.NonNull;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.comment.Comment;
import itsc.hackathon.shareapp.data.network.model.topic.Topic;
import itsc.hackathon.shareapp.ui.base.BaseViewHolder;
import itsc.hackathon.shareapp.ui.detail.DetailAdapter;
import itsc.hackathon.shareapp.ui.post.CallBack;

public class SubscriptionAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Topic> topics;
    private Callback mCallback;

    public SubscriptionAdapter(List<Topic> topics) {
        this.topics = topics;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_topic, viewGroup, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder sensorViewHolder, int i) {
        sensorViewHolder.onBind(i);
    }

    public void addItems(List<Topic> topics) {
        this.topics.clear();
        this.topics.addAll(topics);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(Topic topic);
    }

    public class SensorViewHolder extends BaseViewHolder {

        @BindView(R.id.card_topic_title)
        TextView mTopicTitle;

        @BindView(R.id.card_topic_thumbnail)
        ImageView mTopicThumbnail;

        Topic topic;

        public SensorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (topics != null) {

                topic = topics.get(position);

                // title
                if (!TextUtils.isEmpty(topic.getName()))
                    mTopicTitle.setText(String.valueOf(topic.getName()));

            } else {
                // todo find a better way of handling this condition
            }

            itemView.setOnClickListener(v -> mCallback.onItemClicked(topics.get(getAdapterPosition())));

        }

        @Override
        protected void clear() {
            mTopicTitle.setText("");
        }
    }
}