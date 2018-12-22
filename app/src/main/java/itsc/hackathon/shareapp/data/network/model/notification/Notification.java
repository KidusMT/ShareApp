package itsc.hackathon.shareapp.data.network.model.notification;

public class Notification {

    String notificationTitle;
    String notificationDate;

    public Notification(String notificationTitle, String notificationDate) {
        this.notificationDate = notificationDate;
        this.notificationTitle = notificationTitle;
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

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }
}
