package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityUserPageBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserPageBinding act_user = ActivityUserPageBinding.inflate(getLayoutInflater());
        setContentView(act_user.getRoot());
        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvName = act_user.tvName;
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
}