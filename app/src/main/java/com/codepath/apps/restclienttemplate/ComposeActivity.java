package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcel;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {
    public static final int MAX_LENGTH = 140;
    public static final String TAG ="ComposeActivity";
    Button btnTweet;
    EditText etCompose;
    TwitterClient client;
    ImageView ivProfileImage;
    TextView tvName;
    TextView tvDisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        btnTweet = findViewById(R.id.btnTweet);
        etCompose = findViewById(R.id.etCompose);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvName = findViewById(R.id.tvName);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        client = TwitterApp.getRestClient(this);
        client.myInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    User user = User.fromJson(json.jsonObject);
                    tvName.setText(user.name);
                    tvDisplayName.setText("@"+user.screenName);
                    Glide.with(getApplicationContext()).load(user.publicImageURL).circleCrop().into(ivProfileImage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        etCompose.setText(String.format("@%s ", tweet.user.screenName));
        //set onclick listener to the tweet button

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this,"Sorry, your tweet can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tweetContent.length()>MAX_LENGTH) {
                    Toast.makeText(ComposeActivity.this,"Sorry, your tweet is too long",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Make a call to the Twitter API to publish
                client.replyTweet(tweetContent, tweet.id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG,"onsuccess");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG,"published tweet says"+tweet.body);
                            Intent data = new Intent();
                            data.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK, data);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG,"onfailed",throwable);
                    }
                });
            }
        });
    }
}