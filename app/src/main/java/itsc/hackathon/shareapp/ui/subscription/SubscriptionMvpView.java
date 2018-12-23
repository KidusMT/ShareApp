package itsc.hackathon.shareapp.ui.subscription;

import java.util.List;

import itsc.hackathon.shareapp.data.network.model.topic.Topic;
import itsc.hackathon.shareapp.ui.base.MvpView;

/**
 * Created by KidusMT.
 */

public interface SubscriptionMvpView extends MvpView {

    void showSubscriptions(List<Topic> topics);
    void openDetailSensorActivity(Topic topics);
    void loadPage();
}
