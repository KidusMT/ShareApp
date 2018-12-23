package itsc.hackathon.shareapp.ui.topic;

import java.util.List;

import itsc.hackathon.shareapp.data.network.model.notification.Notification;
import itsc.hackathon.shareapp.data.network.model.topic.Topic;
import itsc.hackathon.shareapp.ui.base.MvpView;

public interface TopicMvpView extends MvpView {

    void showSubscriptions(List<Topic> topics);
    void openDetailSensorActivity(Topic topics);
    void loadPage();
}
