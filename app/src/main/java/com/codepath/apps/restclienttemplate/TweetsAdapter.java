package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import okhttp3.Headers;

import static com.codepath.apps.restclienttemplate.TimelineActivity.REQUEST_CODE;


public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    public interface OnClickListener {
        void onItemClicked (int position);
    }
    Context context;
    OnClickListener onClickListener;
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
        this.onClickListener = onClickListener;
    }
    TwitterClient client = TwitterApp.getRestClient(context);
    List<Tweet> tweets;

    //for each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    //bind values based on position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the data
        Tweet tweet = tweets.get(position);
        //bind it with the view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    //clear tweets from the recyclerview
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items (tweets)
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    //define a viewholder

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //itemView is one row in recyclerview

        ImageView ivProfileImage;
        TextView tvName;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvTimeSince;
        ImageView ivMedia;
        ImageView ivLike;
        ImageView ivRetweet;
        ImageView ivReply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvScreenName = itemView.findViewById(R.id.tvDisplayName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvTimeSince = itemView.findViewById(R.id.tvTimeSince);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            tvName = itemView.findViewById(R.id.tvName);
            //itemView.setOnClickListener(this);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivRetweet = itemView.findViewById(R.id.ivRetweet);
            ivReply = itemView.findViewById(R.id.ivReply);
            ivLike.setOnClickListener(this);
            ivRetweet.setOnClickListener(this);
            ivReply.setOnClickListener(this);
            tvBody.setOnClickListener(this);
            ivLike.setTag(R.drawable.ic_vector_heart_stroke);
            ivRetweet.setTag(R.drawable.ic_vector_retweet_stroke);


        }

        public String getRelativeTimeAgo(String rawJsonDate) {
            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            sf.setLenient(true);

            String relativeDate = "";
            try {
                long dateMillis = sf.parse(rawJsonDate).getTime();
                relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return relativeDate;
        }

        public void onClick(View v) {
            Log.i("hello","hello");
            int position = getAdapterPosition();
            Tweet tweet = tweets.get(position);

            if (v.getId()==ivLike.getId()) {
                if ((Integer) v.getTag() == R.drawable.ic_vector_heart_stroke) {
                    client.likeTweet(tweet.id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        }
                    });
                    ivLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vector_heart));
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
                    ivLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vector_heart_stroke));
                    ivLike.setTag(R.drawable.ic_vector_heart_stroke);
                    ivLike.setColorFilter(Color.GRAY);
                }
            };
            if (v.getId()==ivRetweet.getId()) {
                if ((Integer) v.getTag() == R.drawable.ic_vector_retweet_stroke) {
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
                    ivRetweet.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vector_retweet));
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
                    ivRetweet.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vector_retweet_stroke));
                    ivRetweet.setTag(R.drawable.ic_vector_retweet_stroke);
                    ivRetweet.setColorFilter(Color.GRAY);
                }
            };
            if (v.getId()==ivReply.getId()) {
                Intent intent = new Intent(context, ComposeActivity.class);
                context.startActivity(intent);
            }
            if (v.getId()==tvBody.getId()) {
                Intent intent = new Intent(context, TweetDetails.class);
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                context.startActivity(intent);
            }
        }
        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText("@"+tweet.user.screenName);
            tvName.setText(tweet.user.name);
            Glide.with(context).load(tweet.user.publicImageURL).circleCrop().into(ivProfileImage);
            tvTimeSince.setText(getRelativeTimeAgo(tweet.createdAt));
            if (tweet.mediaURL != null) {
                Glide.with(context).load(tweet.mediaURL).centerCrop().into(ivMedia);
            }
            else {
                ivMedia.setVisibility(View.GONE);
            }

        }
    }
}
