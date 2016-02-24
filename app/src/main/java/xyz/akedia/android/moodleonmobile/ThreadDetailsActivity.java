package xyz.akedia.android.moodleonmobile;

import android.app.Dialog;
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

import java.util.ArrayList;
import java.util.List;

import xyz.akedia.android.moodleonmobile.Adapters.CommentAdapter;

public class ThreadDetailsActivity extends AppCompatActivity {

    String threadTitle;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        threadTitle = getIntent().getStringExtra("courseCode");
        toolbar.setTitle("Thread details");
        setSupportActionBar(toolbar);
        init();
    }
    private void init(){
        RecyclerView commentList = (RecyclerView)findViewById(R.id.comment_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        commentList.setHasFixedSize(true);
        commentList.setLayoutManager(llm);
        View headerView = getLayoutInflater().inflate(R.layout.layout_thread_view,null,false);
        View footerView = getLayoutInflater().inflate(R.layout.layout_comment_footer,null,false);
        CommentAdapter commentAdapter = new CommentAdapter(getDummyComments(),headerView,footerView);
        commentList.setAdapter(commentAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }
    private List<Comment> getDummyComments(){
        List<Comment> comments = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            Comment comment = new Comment("This is a sample comment!","2 days, 9 hours ago","Ashish Ranjan");
            comments.add(comment);
        }
        return comments;
    }

}
