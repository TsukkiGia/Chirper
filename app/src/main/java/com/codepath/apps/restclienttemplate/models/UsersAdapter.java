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
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import okhttp3.Headers;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    public interface OnClickListener {
        void onItemClicked (int position);
    }

    Context context;
    OnClickListener onClickListener;
    public UsersAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
        this.onClickListener = onClickListener;
    }
    TwitterClient client = TwitterApp.getRestClient(context);
    List<User> users;

    //for each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    //bind values based on position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the data
        User user = users.get(position);
        //bind it with the view holder
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    //clear users from the recyclerview
    public void clear() {
        users.clear();
        notifyDataSetChanged();
    }

    // Add a list of items (users)
    public void addAll(List<User> userList) {
        users.addAll(userList);
        notifyDataSetChanged();
    }

    //define a viewholder

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //itemView is one row in recyclerview

        TextView tvName;
        TextView tvScreenName;
        TextView tvBio;
        ImageView ivProfileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvScreenName = itemView.findViewById(R.id.tvDisplayName);
            tvBio = itemView.findViewById(R.id.tvBio);
            tvName = itemView.findViewById(R.id.tvName);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);

        }

        public void onClick(View v) {
            int position = getAdapterPosition();
            User user = users.get(position);
        }

        public void bind(User user) {
            tvBio.setText(user.bio);
            tvScreenName.setText("@"+user.screenName);
            tvName.setText(user.name);
            Glide.with(context).load(user.publicImageURL).circleCrop().into(ivProfileImage);
        }
    }
}
