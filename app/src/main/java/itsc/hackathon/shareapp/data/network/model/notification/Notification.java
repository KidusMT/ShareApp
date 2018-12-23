package itsc.hackathon.shareapp.data.network.model.notification;

public class Notification {

    String notificationTitle;
    String notificationTopic;
    String notificationDate;

    public Notification(String notificationTitle, String notificationDate, String notificationTopic) {
        this.notificationDate = notificationDate;
        this.notificationTitle = notificationTitle;
        this.notificationTopic =  notificationTopic;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public String getNotificationTopic() {
        return notificationTopic;
    }

    public void setNotificationTopic(String notificationTopic) {
        this.notificationTopic = notificationTopic;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;


    }
}
