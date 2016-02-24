package xyz.akedia.android.moodleonmobile.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.akedia.android.moodleonmobile.R;
import xyz.akedia.android.moodleonmobile.model.Comment;
import xyz.akedia.android.moodleonmobile.model.User;
import xyz.akedia.android.moodleonmobile.model.Users;

/**
 * Created by ashish on 24/2/16.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView creatorName, createdDate, content;
        CardView cardView;

        CommentViewHolder(final View itemView) {
            super(itemView);
            creatorName = (TextView)itemView.findViewById(R.id.creator_name);
            createdDate = (TextView)itemView.findViewById(R.id.created_date);
            content = (TextView)itemView.findViewById(R.id.content);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
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
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(final View itemView) {
            super(itemView);
        }
    }
    List<Comment> commentList;
    View mHeaderView,mFooterView;
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_FOOTER = 2;
//    Activity parentActivity;

    public CommentAdapter(List<Comment> list,View headerView,View footerView){
        this.commentList = list;
        this.mHeaderView = headerView;
        this.mFooterView = footerView;
//        this.parentActivity = activity;
    }
    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return commentList.size() + 1;
        } else {
            return commentList.size() + 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return VIEW_TYPE_HEADER;
        else if(position == commentList.size() + 1)
            return VIEW_TYPE_FOOTER;
        else
            return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        }else if (i == VIEW_TYPE_FOOTER){
            return new FooterViewHolder(mFooterView);
        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment_list, viewGroup, false);
            CommentViewHolder commentViewHolder = new CommentViewHolder(v);
            return commentViewHolder;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CommentViewHolder) {
            CommentViewHolder commentViewHolder = (CommentViewHolder) viewHolder;
            Comment comment = commentList.get(i-1);
            commentViewHolder.creatorName.setText(comment.commenterName);
            commentViewHolder.content.setText(comment.description);
            commentViewHolder.createdDate.setText(comment.createdTime);
        }else if(viewHolder instanceof HeaderViewHolder){
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

    public void updateCommentList(ArrayList<Comment> newComments) {
        commentList = newComments;
    }
}
