package itsc.hackathon.shareapp.ui.post;

import itsc.hackathon.shareapp.data.network.model.post.Post;

public interface PostCommunicator {

    void fabClicked();
    void onItemClicked(Post post);
}
