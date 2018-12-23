package itsc.hackathon.shareapp.ui.detail;

import android.app.Dialog;
import android.hardware.Sensor;

import java.util.List;

import itsc.hackathon.shareapp.data.network.model.comment.Comment;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.ui.base.MvpView;
import okhttp3.ResponseBody;

/**
 * Created by KidusMT.
 */

public interface DetailPostMvpView extends MvpView {

    void showComments(List<Comment> comments);
    void openDetailSensorActivity(Sensor sensor);
    void writeResponseBodyToDisk(ResponseBody body, Dialog dialog);
    void loadPage(Post post);
}
