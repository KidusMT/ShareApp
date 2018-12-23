package itsc.hackathon.shareapp.ui.notification;

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
import itsc.hackathon.shareapp.data.network.model.notification.Notification;
import itsc.hackathon.shareapp.ui.base.BaseViewHolder;

public class NotificationAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Notification> notifications;
    private Callback mCallback;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
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

    public void addItems(List<Notification> notificationResponse) {
        this.notifications.clear();
        this.notifications.addAll(notificationResponse);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(Notification notification);
    }

    public class SensorViewHolder extends BaseViewHolder {

        @BindView(R.id.card_notification_title)
        TextView notificationTitle;

        @BindView(R.id.card_notification_date)
        TextView notificationDate;

        @BindView(R.id.card_notification_topic)
        TextView notificationTopic;

        @BindView(R.id.card_notification_icon)
        ImageView notificationIcon;

        Notification notification;

        public SensorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (notifications != null) {

                notification = notifications.get(position);

                notificationTitle.setText(notification.getNotificationTitle());
                notificationDate.setText(notification.getNotificationDate());
                notificationTopic.setText(notification.getNotificationTopic());

            } else {
                // todo find a better way of handling this condition
            }

            itemView.setOnClickListener(v -> mCallback.onItemClicked(notifications.get(getAdapterPosition())));

        }

        @Override
        protected void clear() {
            notificationTitle.setText("");
            notificationDate.setText("");
            notificationTopic.setText("");
        }
    }
}
