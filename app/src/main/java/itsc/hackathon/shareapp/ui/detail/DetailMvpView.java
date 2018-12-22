package itsc.hackathon.shareapp.ui.detail;

import android.hardware.Sensor;

import java.util.List;

import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.ui.base.MvpView;

/**
 * Created by KidusMT.
 */

public interface DetailMvpView extends MvpView {

    void showPosts(List<Post> posts);
    void openDetailSensorActivity(Sensor sensor);
    void loadPage();
}
