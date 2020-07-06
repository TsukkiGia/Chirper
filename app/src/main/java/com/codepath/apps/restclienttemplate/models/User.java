package com.codepath.apps.restclienttemplate.models;

import androidx.room.Entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class User {
    public String name;

    public User() {
    }

    public String screenName;
    public String publicImageURL;
    public String BannerImageURL;
    public long id;
    public long followerCount;
    public long followingCount;
    public String bio;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name =  jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.publicImageURL = jsonObject.getString("profile_image_url_https");
        user.id = jsonObject.getLong("id");
        user.followerCount = jsonObject.getLong("followers_count");
        user.followingCount = jsonObject.getLong("friends_count");
        try {
            user.BannerImageURL = jsonObject.getString("profile_banner_url");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        user.bio = jsonObject.getString("description");
        return user;
    }

    public static List<User> fromJsonArray (JSONArray jsonArray) throws JSONException{
        List<User> users = new ArrayList<>();
        for (int i = 0; i<jsonArray.length(); i++) {
            users.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return users;
    }
}
