package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ItemUserBinding;
import com.codepath.apps.restclienttemplate.models.User;

import java.util.List;



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
    List<User> users;
    ItemUserBinding item_bind;

    //for each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        item_bind = ItemUserBinding.inflate(inflate,parent,false);
        return new UsersAdapter.ViewHolder(item_bind);
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

        public ViewHolder(@NonNull ItemUserBinding itemView) {
            super(itemView.getRoot());
            tvScreenName = itemView.tvDisplayName;
            tvBio = itemView.tvBio;
            tvName = itemView.tvName;
            ivProfileImage = itemView.ivProfileImage;
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
