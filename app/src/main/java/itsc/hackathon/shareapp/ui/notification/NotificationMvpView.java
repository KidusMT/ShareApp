package itsc.hackathon.shareapp.ui.notification;

import java.util.List;

import itsc.hackathon.shareapp.data.network.model.notification.Notification;
import itsc.hackathon.shareapp.ui.base.MvpView;

public interface NotificationMvpView extends MvpView {

    void showNotifications(List<Notification> notificationResponses);
}
