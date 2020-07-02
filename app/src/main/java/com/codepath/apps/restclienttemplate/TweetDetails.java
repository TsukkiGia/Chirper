package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetails extends AppCompatActivity {
    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvName;
    TextView tvDisplayName;
    ImageView ivMedia;
    ImageView ivLike;
    ImageView ivRetweet;
    ImageView ivReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvBody = findViewById(R.id.tvBody);
        tvName = findViewById(R.id.tvName);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        ivMedia = findViewById(R.id.ivMedia);
        ivLike = findViewById(R.id.ivLike);
        ivLike.setTag(R.drawable.ic_vector_heart_stroke);
        ivReply = findViewById(R.id.ivReply);
        ivRetweet = findViewById(R.id.ivRetweet);
        ivRetweet.setTag(R.drawable.ic_vector_retweet_stroke);
        tvBody.setText(tweet.body);
        tvName.setText(tweet.user.name);
        tvDisplayName.setText("@"+tweet.user.screenName);
        Log.i("hello",tweet.user.publicImageURL);
        Glide.with(getApplicationContext()).load(tweet.user.publicImageURL).circleCrop().into(ivProfileImage);
        if (tweet.mediaURL != null) {
            Glide.with(getApplicationContext()).load(tweet.mediaURL).centerCrop().into(ivMedia);
        }
        else {
            ivMedia.setVisibility(View.GONE);
        }
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewProfile = new Intent(getApplicationContext(),UserPage.class);
                viewProfile.putExtra(Tweet.class.getSimpleName(),Parcels.wrap(tweet));
                startActivity(viewProfile);
            }
        });
        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = (ImageView) view;
                if ((Integer) imageView.getTag() == R.drawable.ic_vector_heart_stroke) {
                    ivLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_heart));
                    ivLike.setTag(R.drawable.ic_vector_heart);
                    ivLike.setColorFilter(Color.RED);
                }
                else {
                    ivLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_heart_stroke));
                    ivLike.setTag(R.drawable.ic_vector_heart_stroke);
                    ivLike.setColorFilter(Color.GRAY);
                }
            }
        });
        ivRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = (ImageView) view;
                if ((Integer) imageView.getTag() == R.drawable.ic_vector_retweet_stroke) {
                    ivRetweet.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_retweet));
                    ivRetweet.setTag(R.drawable.ic_vector_retweet);
                    ivRetweet.setColorFilter(Color.GREEN);
                }
                else {
                    ivRetweet.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_retweet_stroke));
                    ivRetweet.setTag(R.drawable.ic_vector_retweet_stroke);
                    ivRetweet.setColorFilter(Color.GRAY);
                }
            }
        });
    }
    private int getDrawableId(ImageView iv) {
        return (Integer) iv.getTag();
    }
}