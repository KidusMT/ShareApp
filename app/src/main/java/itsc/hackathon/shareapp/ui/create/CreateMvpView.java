package itsc.hackathon.shareapp.ui.create;

import java.util.List;

import itsc.hackathon.shareapp.data.network.model.notification.Notification;
import itsc.hackathon.shareapp.ui.base.MvpView;

public interface CreateMvpView extends MvpView {

    void showNotifications(List<Notification> notificationResponses);
}
