package itsc.hackathon.shareapp.ui.topic;

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
import itsc.hackathon.shareapp.data.network.model.topic.Topic;
import itsc.hackathon.shareapp.ui.base.BaseViewHolder;

public class TopicAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Topic> topics;
    private Callback mCallback;

    public TopicAdapter(List<Topic> topics) {
        this.topics = topics;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_notification, viewGroup, false);
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
        TextView topicTitle;

        @BindView(R.id.card_topic_thumbnail)
        ImageView topicThumbnail;

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

//                // sensor name
//                if (topic.getCondition().getSensors().size() > 0) {
//                    for (int i = 0; i < topic.getCondition().getSensors().size(); i++) {
//                        mNotificationSensorName.setText(String.valueOf(
//                                (topic.getCondition().getSensors().size() == 1) ? topic.getCondition().getSensors().get(i)
//                                        : (topic.getCondition().getSensors().size() == i) ? topic.getCondition().getSensors().get(i)
//                                        : topic.getCondition().getSensors().get(i) + ", "));
//                    }
//                } else {
//                    mNotificationSensorName.setVisibility(View.GONE);
//                }
//
//                // measurement
//                if (topic.getCondition().getMeasurements().size() > 0) {
//                    for (int i = 0; i < topic.getCondition().getMeasurements().size(); i++) {
//                        mNotificationMeasurement.setText(String.valueOf(
//                                (topic.getCondition().getMeasurements().size() == 1) ? topic.getCondition().getMeasurements().get(i)
//                                        : (topic.getCondition().getMeasurements().size() == i) ? topic.getCondition().getMeasurements().get(i)
//                                        : topic.getCondition().getMeasurements().get(i) + ", "));
//                    }
//                } else {
//                    mNotificationMeasurement.setVisibility(View.GONE);
//                }
//
//                // expression
//                if (!TextUtils.isEmpty(topic.getCondition().getExpression())) {
//                    mNotificationExpression.setText(String.valueOf(topic.getCondition().getExpression()));
//                } else {
//                    mNotificationExpression.setVisibility(View.GONE);
//                }
//
//                // message
//                if (!TextUtils.isEmpty(topic.getNotification().getMessage())) {
//                    mNotificationMessage.setText(String.valueOf(topic.getNotification().getMessage()));
//                } else {
//                    mNotificationMessage.setVisibility(View.GONE);
//                }
//
//                // owner
//                if (topic.getNotification().getUsernames().size() > 0) {
//                    for (int i = 0; i < topic.getNotification().getUsernames().size(); i++) {
//                        mNotificationOwner.setText(String.valueOf(
//                                (topic.getNotification().getUsernames().size() == 1) ? topic.getNotification().getUsernames().get(i)
//                                        : (topic.getNotification().getUsernames().size() == i) ? topic.getNotification().getUsernames().get(i)
//                                        : topic.getNotification().getUsernames().get(i) + ", "));
//                    }
//                } else {
//                    mNotificationOwner.setVisibility(View.GONE);
//                }
//
//                // shared at
//                if (topic.getCondition().getSensors().size() > 0) {
//                    for (int i = 0; i < topic.getNotification().getChannels().size(); i++) {
//                        mNotificationSharedAt.setText(String.valueOf(
//                                (topic.getNotification().getChannels().size() == 1) ?
//                                        topic.getNotification().getChannels().get(i)
//                                        : (topic.getNotification().getChannels().size() == i) ?
//                                        topic.getNotification().getChannels().get(i)
//                                        : topic.getNotification().getChannels().get(i) + ", "));
//                    }
//                } else {
//                    mNotificationSharedAt.setVisibility(View.GONE);
//                }

            } else {
                // todo find a better way of handling this condition
            }

            itemView.setOnClickListener(v -> mCallback.onItemClicked(topics.get(getAdapterPosition())));

        }

        @Override
        protected void clear() {
            topicTitle.setText("");
        }
    }
}
