package itsc.hackathon.shareapp.ui.subscription;

import itsc.hackathon.shareapp.data.network.model.topic.Topic;

public interface SubscriptionCommunicator {
    void onItemClicked(Topic topic);
}
