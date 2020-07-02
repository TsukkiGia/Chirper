package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
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
        user.BannerImageURL = jsonObject.getString("profile_banner_url");
        user.bio = jsonObject.getString("description");

        return user;
    }
}
