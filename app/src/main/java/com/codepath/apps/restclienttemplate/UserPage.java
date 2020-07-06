package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityUserPageBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class UserPage extends AppCompatActivity {
    ImageView ivBanner;
    ImageView ivProfileImage;
    TextView tvName;
    TextView tvDisplayName;
    TextView tvBio;
    TextView tvFollowerNumber;
    TextView tvFollowingNumber;
    TextView tvFollowerText;
    TextView tvFollowingText;
    RecyclerView rvHomeTweets;
    TweetsAdapter adapter;
    List<Tweet> tweets;
    TwitterClient client;
    Tweet tweet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserPageBinding act_user = ActivityUserPageBinding.inflate(getLayoutInflater());
        setContentView(act_user.getRoot());
        tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvName = act_user.tvName;
        tweets = new ArrayList<>();
        client = TwitterApp.getRestClient(this);
        tvDisplayName = act_user.tvDisplayName;
        ivProfileImage = act_user.ivProfileImage;
        tvBio = act_user.tvBio;
        ivBanner = act_user.ivBanner;
        tvFollowerNumber = act_user.tvFollowerNumber;
        tvFollowingNumber = act_user.tvFollowingNumber;
        tvFollowerText = act_user.tvFollowerText;
        tvFollowingText = act_user.tvFollowingText;
        tvFollowerNumber.setText(String.valueOf(tweet.user.followerCount));
        tvFollowingNumber.setText(String.valueOf(tweet.user.followingCount));
        adapter = new TweetsAdapter(this,tweets);
        rvHomeTweets = act_user.rvHomeTweets;
        rvHomeTweets.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHomeTweets.setLayoutManager(layoutManager);
        DividerItemDecoration decor =  new DividerItemDecoration(rvHomeTweets.getContext(),layoutManager.getOrientation());
        rvHomeTweets.addItemDecoration(decor);
        populateUserTimeline();
        tvName.setText(tweet.user.name);
        tvDisplayName.setText("@"+tweet.user.screenName);
        tvBio.setText(tweet.user.bio);
        Log.i("hello",tweet.user.publicImageURL);
        Glide.with(getApplicationContext()).load(tweet.user.publicImageURL).circleCrop().into(ivProfileImage);
        Glide.with(getApplicationContext()).load(tweet.user.BannerImageURL).centerCrop().into(ivBanner);
        tvFollowerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserPage.this, FollowerList.class);
                i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                startActivity(i);
            }
        });

        tvFollowingNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserPage.this, FollowingList.class);
                i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                startActivity(i);
            }
        });

    }

    private void populateUserTimeline() {
        client.getTheTweets(tweet.user.screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray jsonArray = json.jsonArray;
                try {
                    adapter.clear();
                    adapter.addAll(Tweet.fromJsonArray(jsonArray));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
    }
}
