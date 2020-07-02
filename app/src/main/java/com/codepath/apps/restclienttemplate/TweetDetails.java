package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.EditNameDialogFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class TweetDetails extends AppCompatActivity {
    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvName;
    TextView tvDisplayName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvBody = findViewById(R.id.tvBody);
        tvName = findViewById(R.id.tvName);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvBody.setText(tweet.body);
        tvName.setText(tweet.user.name);
        tvDisplayName.setText(tweet.user.screenName);
        Log.i("hello",tweet.user.publicImageURL);
        Glide.with(getApplicationContext()).load(tweet.user.publicImageURL).circleCrop().into(ivProfileImage);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewProfile = new Intent(getApplicationContext(),UserPage.class);
                viewProfile.putExtra("Name",tweet.user.name);
                viewProfile.putExtra("Screen",tweet.user.screenName);
                viewProfile.putExtra("ID",tweet.user.id);
                startActivity(viewProfile);

            }
        });
    }

}