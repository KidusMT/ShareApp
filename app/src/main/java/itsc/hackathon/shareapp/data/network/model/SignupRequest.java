package itsc.hackathon.shareapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupRequest {
    private SignupRequest(){

    }
    public static class ServerSignupRequest{
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;

        public String getFirstname() {
            return firstname;
        }

        public ServerSignupRequest(String firstname, String username, String email, String password) {
            this.firstname = firstname;
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }
}
