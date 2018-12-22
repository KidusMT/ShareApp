
package itsc.hackathon.shareapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import itsc.hackathon.shareapp.data.network.model.user.User;

public class LoginResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ttl")
    @Expose
    private Integer ttl;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("user")
    @Expose
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
