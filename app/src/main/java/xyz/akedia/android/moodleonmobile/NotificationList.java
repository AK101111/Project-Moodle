package xyz.akedia.android.moodleonmobile;

import java.util.ArrayList;
import java.util.List;

import xyz.akedia.android.moodleonmobile.model.Notification;

/**
 * Created by ashish on 24/2/16.
 */
public class NotificationList {
    private List<Notification> notificationList;

    public NotificationList(){
        notificationList = new ArrayList<>();
    }
    public void addNotification(Notification notification){
        this.notificationList.add(notification);
    }
    public Notification getNotificationAt(int position){
        return this.notificationList.get(position);
    }
    public int notificationCount(){
        return notificationList.size();
    }
}
