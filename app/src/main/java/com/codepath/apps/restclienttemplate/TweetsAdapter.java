package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvTimeSince = itemView.findViewById(R.id.tvTimeSince);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            //ivLike = itemView.findViewById(R.id.ivLike);
            //ivRetweet = itemView.findViewById(R.id.ivRetweet);
            //ivReply = itemView.findViewById(R.id.ivReply);

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
            if(position != RecyclerView.NO_POSITION) {
                Tweet tweet = tweets.get(position);
                Intent intent = new Intent(context, TweetDetails.class);
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                context.startActivity(intent);
            }

        }
        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            Glide.with(context).load(tweet.user.publicImageURL).circleCrop().into(ivProfileImage);
            tvTimeSince.setText(getRelativeTimeAgo(tweet.createdAt));
            if (tweet.mediaURL != null) {
                Glide.with(context).load(tweet.mediaURL).transform(new RoundedCornersTransformation(30, 10)).into(ivMedia);
            }
            else {
                ivMedia.setVisibility(View.GONE);
            }
            //ivReply.setImageResource(R.drawable.reply);
            //ivRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
            //ivLike.setImageResource(R.drawable.ic_vector_heart_stroke);
        }
    }
}
