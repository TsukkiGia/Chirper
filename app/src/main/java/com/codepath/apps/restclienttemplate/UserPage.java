package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class UserPage extends AppCompatActivity {
    ImageView ivBanner;
    ImageView ivProfileImage;
    TextView tvName;
    TextView tvDisplayName;
    TextView tvBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvName = findViewById(R.id.tvName);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvBio = findViewById(R.id.tvBio);
        ivBanner = findViewById(R.id.ivBanner);
        tvName.setText(tweet.user.name);
        tvDisplayName.setText("@"+tweet.user.screenName);
        tvBio.setText(tweet.user.bio);
        Log.i("hello",tweet.user.publicImageURL);
        Glide.with(getApplicationContext()).load(tweet.user.publicImageURL).circleCrop().into(ivProfileImage);
        Glide.with(getApplicationContext()).load(tweet.user.BannerImageURL).centerCrop().into(ivBanner);

    }
}