package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Tweet {

    public Tweet() {
    }

    public String body;
    public String createdAt;
    public User user;
    public String mediaURL;
    public long id;
    public long retweetCount;
    public long likeCount;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        //how do you create an object within the method
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");
        tweet.retweetCount = jsonObject.getLong("retweet_count");
        //tweet.likeCount = jsonObject.getLong("favourites_count");
        try {
            tweet.mediaURL = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;

    }
    public static List<Tweet> fromJsonArray (JSONArray jsonArray) throws JSONException{
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i<jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
