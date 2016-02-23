package xyz.akedia.android.moodleonmobile.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.akedia.android.moodleonmobile.Comment;
import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.ThreadDetailsActivity;

/**
 * Created by ashish on 24/2/16.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView creatorName, createdDate, content;

        CommentViewHolder(final View itemView) {
            super(itemView);
            creatorName = (TextView)itemView.findViewById(R.id.creator_name);
            createdDate = (TextView)itemView.findViewById(R.id.created_date);
            content = (TextView)itemView.findViewById(R.id.content);
        }
    }
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView threadTitle, threadDescription, created, lastUpdated,commentIndicator;
        HeaderViewHolder(final View itemView) {
            super(itemView);
            threadTitle = (TextView)itemView.findViewById(R.id.thread_title);
            threadDescription = (TextView)itemView.findViewById(R.id.thread_description);
            created = (TextView)itemView.findViewById(R.id.created_time_notice);
            lastUpdated = (TextView)itemView.findViewById(R.id.updated_time_notice);
            commentIndicator = (TextView)itemView.findViewById(R.id.comment_indicator);
        }
    }
    List<Comment> commentList;
    View mHeaderView;
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
//    Activity parentActivity;

    public CommentAdapter(List<Comment> list,View headerView){
        this.commentList = list;
        this.mHeaderView = headerView;
//        this.parentActivity = activity;
    }
    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return commentList.size();
        } else {
            return commentList.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comment, viewGroup, false);
            CommentViewHolder commentViewHolder = new CommentViewHolder(v);
            return commentViewHolder;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CommentViewHolder) {
            CommentViewHolder commentViewHolder = (CommentViewHolder) viewHolder;
            Comment comment = commentList.get(i-1);
            commentViewHolder.creatorName.setText(comment.creatorName);
            commentViewHolder.content.setText(comment.content);
            commentViewHolder.createdDate.setText(comment.createdDate);
        }else{
            //set thread details(title, description etc.) here
            HeaderViewHolder headerViewHolder = (HeaderViewHolder)viewHolder;
            if(getItemCount() == 1)
                headerViewHolder.commentIndicator.setText("No comments to view");
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
