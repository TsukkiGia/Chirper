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
import com.codepath.apps.restclienttemplate.databinding.ActivityTweetDetailsBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;

import okhttp3.Headers;

import static com.codepath.apps.restclienttemplate.TimelineActivity.REQUEST_CODE;

public class TweetDetails extends AppCompatActivity {
    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvName;
    TextView tvDisplayName;
    ImageView ivMedia;
    ImageView ivLike;
    ImageView ivRetweet;
    ImageView ivReply;
    ImageView ivExit;
    TextView tvRT;
    TextView tvLikes;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTweetDetailsBinding act_det = ActivityTweetDetailsBinding.inflate(getLayoutInflater());
        setContentView(act_det.getRoot());
        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        final String liketag = getIntent().getStringExtra("like tag");
        final String retweettag = getIntent().getStringExtra("retweet tag");
        client = TwitterApp.getRestClient(this);
        tvBody = act_det.tvBody;
        tvName = act_det.tvName;
        tvLikes = act_det.tvLike;
        tvRT = act_det.tvRT;
        tvDisplayName = act_det.tvDisplayName;
        ivProfileImage = act_det.ivProfileImage;
        ivMedia = act_det.ivMedia;
        ivExit = act_det.ivExit;
        ivLike = act_det.ivLike;
        ivRetweet = act_det.ivRetweet;
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("like tag",String.valueOf(ivLike.getTag()));
                intent.putExtra("retweet tag",String.valueOf(ivRetweet.getTag()));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        if (liketag.equals(String.valueOf(R.drawable.ic_vector_heart_stroke))) {
            ivLike.setTag(R.drawable.ic_vector_heart_stroke);
        }
        else {
            ivLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_heart));
            ivLike.setTag(R.drawable.ic_vector_heart);
            ivLike.setColorFilter(Color.RED);
        }
        if (retweettag.equals(String.valueOf(R.drawable.ic_vector_retweet_stroke))) {
            ivRetweet.setTag(R.drawable.ic_vector_retweet_stroke);
        }
        else {
            ivRetweet.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_retweet));
            ivRetweet.setTag(R.drawable.ic_vector_retweet);
            ivRetweet.setColorFilter(Color.GREEN);
        }
        ivReply = act_det.ivReply;
        tvLikes.setText(String.valueOf(tweet.likeCount));
        tvRT.setText(String.valueOf(tweet.retweetCount));
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
                    client.likeTweet(tweet.id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        }
                    });
                    ivLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_heart));
                    ivLike.setTag(R.drawable.ic_vector_heart);
                    ivLike.setColorFilter(Color.RED);
                }
                else {
                    client.unlikeTweet(tweet.id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i("check",json.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.i("check",response);
                        }
                    });
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
                    client.reTweet(tweet.id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i("check",json.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.i("check",response);
                        }
                    });
                    ivRetweet.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_retweet));
                    ivRetweet.setTag(R.drawable.ic_vector_retweet);
                    ivRetweet.setColorFilter(Color.GREEN);
                }
                else {
                    client.unreTweet(tweet.id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i("check",json.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.i("check",response);
                        }
                    });
                    ivRetweet.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_retweet_stroke));
                    ivRetweet.setTag(R.drawable.ic_vector_retweet_stroke);
                    ivRetweet.setColorFilter(Color.GRAY);
                }
            }
        });
        ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TweetDetails.this, ComposeActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }
    private int getDrawableId(ImageView iv) {
        return (Integer) iv.getTag();
    }
}