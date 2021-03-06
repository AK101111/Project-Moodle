package xyz.akedia.android.moodleonmobile;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.akedia.android.moodleonmobile.Adapters.CommentAdapter;
import xyz.akedia.android.moodleonmobile.app.MoodleOnMobile;
import xyz.akedia.android.moodleonmobile.controllers.AsyncResponseHandler;
import xyz.akedia.android.moodleonmobile.controllers.ThreadDetailsController;
import xyz.akedia.android.moodleonmobile.model.Comment;
import xyz.akedia.android.moodleonmobile.model.Thread;
import xyz.akedia.android.moodleonmobile.network.SendComment;

public class ThreadDetailsActivity extends AppCompatActivity {

    public static String courseCode;
    public static int threadId;
    Thread thread;
//    CommentAdapter commentAdapter;

    SwipeRefreshLayout swipeRefreshLayout;
    private CommentAdapter commentAdapter;
    private RecyclerView commentList;

    private String getFilledComment() {
        EditText newComment = (EditText) findViewById(R.id.new_comment);
        return newComment.getText().toString();
    }

    private void clearComment() {
        ((EditText) findViewById(R.id.new_comment)).getText().clear();
    }

    private void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendComment(View v) {
        String comment = getFilledComment();
        hideKeyboard(v);
        new SendComment(threadId, comment, new SendComment.ResponseHandler() {
            @Override
            public void startWait() {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
            }

            @Override
            public void finishWait() {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onSuccess() {
                clearComment();
                Toast.makeText(ThreadDetailsActivity.this,"Comment Posted!",Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
                ThreadDetailsController.getCommentListAsync(courseCode, threadId, new ThreadDetailsController.AsyncResponseHandler() {
                    @Override
                    public void onResponse(ArrayList<Comment> newCommentList) {
                        commentAdapter.updateCommentList(newCommentList);
                        commentList.setAdapter(commentAdapter);
                    }

                    @Override
                    public void duringWait() {

                    }

                    @Override
                    public void finishWait() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure() {
                Toast.makeText(ThreadDetailsActivity.this,"Comment Posted!",Toast.LENGTH_SHORT).show();
            }
        }).send();
    }
    private void onSendClick(View v) {
        sendComment(v);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        courseCode = getIntent().getStringExtra("courseCode");
        threadId = getIntent().getIntExtra("threadId", 1);

        try {
            thread = MoodleOnMobile.getUser().findCourse(courseCode).findThread(threadId);
        } catch (Exception e){
            e.printStackTrace();
        }
        toolbar.setTitle("Thread details");
        setSupportActionBar(toolbar);
        init();

        findViewById(R.id.button_add_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendClick(v);
            }
        });
    }
    private void init(){
//        final RecyclerView commentList = (RecyclerView)findViewById(R.id.comment_list);
        commentList = (RecyclerView)findViewById(R.id.comment_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        commentList.setHasFixedSize(true);
        commentList.setLayoutManager(llm);
        final View headerView = getLayoutInflater().inflate(R.layout.layout_thread_view,null,false);
        final View footerView = getLayoutInflater().inflate(R.layout.layout_comment_footer,null,false);
//        CommentAdapter commentAdapter = new CommentAdapter(getDummyComments(),headerView,footerView);

//        final CommentAdapter commentAdapter = new CommentAdapter(thread,new ArrayList<Comment>(),headerView,footerView);

//        commentAdapter = new CommentAdapter(thread,new ArrayList<Comment>(),headerView,footerView);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);

        ArrayList<Comment> initialCommentList = ThreadDetailsController.getCommentListSync(courseCode,threadId, new ThreadDetailsController.SyncResponseHandler() {
            @Override
            public void onSyncWait() {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
            }
            @Override
            public void finishSyncWait() {
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onUpdate(ArrayList<Comment> updatedComments) {
                commentAdapter = new CommentAdapter(thread,updatedComments,headerView,footerView);
                commentList.setAdapter(commentAdapter);
            }
        });
        commentAdapter = new CommentAdapter(thread,initialCommentList,headerView,footerView);
        commentList.setAdapter(commentAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ThreadDetailsController.getCommentListAsync(courseCode, threadId, new ThreadDetailsController.AsyncResponseHandler() {
                    @Override
                    public void onResponse(ArrayList<Comment> newCommentList) {
                        commentAdapter = new CommentAdapter(thread,newCommentList,headerView,footerView);
                        commentList.setAdapter(commentAdapter);
                    }

                    @Override
                    public void duringWait() {

                    }

                    @Override
                    public void finishWait() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
//    private List<Comment> getDummyComments(){
//        List<Comment> comments = new ArrayList<>();
//        for(int i = 0; i < 12; i++){
//            Comment comment = new Comment("This is a sample comment!","2 days, 9 hours ago","Ashish Ranjan");
//            comments.add(comment);
//        }
//        return comments;
//    }

}
