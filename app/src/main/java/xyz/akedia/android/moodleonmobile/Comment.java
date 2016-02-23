package xyz.akedia.android.moodleonmobile;

/**
 * Created by ashish on 24/2/16.
 */
public class Comment {
    public String content;
    public String createdDate;
    public String creatorName;
    public Comment(String commentContent, String date, String name){
        this.content = commentContent;
        this.createdDate = date;
        this.creatorName = name;
    }
}
