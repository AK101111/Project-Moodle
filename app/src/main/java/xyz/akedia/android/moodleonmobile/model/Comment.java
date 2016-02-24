package xyz.akedia.android.moodleonmobile.model;

/**
 * Created by akedia on 24/02/16.
 */
public class Comment {
    private int commentId;
    public int commenterUserId;
    public String commenterName;
    public String description;
    public String createdTime;

    public Comment(int commentId, int commenterUserId, String commenterName, String description, String createdTime) {
        this.commentId = commentId;
        this.commenterUserId = commenterUserId;
        this.commenterName = commenterName;
        this.description = description;
        this.createdTime = createdTime;
    }

}
