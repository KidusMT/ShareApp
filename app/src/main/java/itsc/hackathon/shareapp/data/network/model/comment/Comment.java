
package itsc.hackathon.shareapp.data.network.model.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("postId")
    @Expose
    private String postId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

}
