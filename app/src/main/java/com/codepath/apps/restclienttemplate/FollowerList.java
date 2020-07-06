package com.codepath.apps.restclienttemplate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.codepath.apps.restclienttemplate.databinding.ActivityFollowerListBinding;
import com.codepath.apps.restclienttemplate.UsersAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class FollowerList extends AppCompatActivity {
    RecyclerView rvFollowers;
    UsersAdapter adapter;
    List<User> users;
    TwitterClient client;
    Toolbar toolbar;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<>();
        tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        ActivityFollowerListBinding act_follower = ActivityFollowerListBinding.inflate(getLayoutInflater());
        setContentView(act_follower.getRoot());
        rvFollowers = act_follower.rvFollowers;
        adapter = new UsersAdapter(this,users);
        toolbar = act_follower.toolbar;
        setSupportActionBar(toolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvFollowers.setLayoutManager(layoutManager);
        rvFollowers.setAdapter(adapter);
        //adding divider between items in the recyclerview
        DividerItemDecoration decor =  new DividerItemDecoration(rvFollowers.getContext(),layoutManager.getOrientation());
        rvFollowers.addItemDecoration(decor);
        client = TwitterApp.getRestClient(this);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        populateFollowers();
        menu.getItem(1).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    private void populateFollowers() {
        client.getFollowers(tweet.user.screenName,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray jsonArray = json.jsonObject.getJSONArray("users");
                    adapter.clear();
                    List<User> userss = new ArrayList<>();
                    for (int i = 0; i<jsonArray.length(); i++) {
                        userss.add(User.fromJson(jsonArray.getJSONObject(i)));
                    }
                    Log.i("test",userss.toString());
                    adapter.addAll(userss);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("FollowerList","failed",throwable);
            }
        });
    }
}
