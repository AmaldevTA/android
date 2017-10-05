package com.poc.infinitescrolllist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;



public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder>{

    private List<Artist> items;
    private Activity activity;

    public ArtistAdapter(Activity activity, List<Artist> items) {
        this.items = items;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.artistName.setText(items.get(position).getName());
        holder.popularity.setText("Popularity " +items.get(position).getPopularity());
        holder.followers.setText("Followers " +items.get(position).getFollowers());

        if(items.get(position).getImage() != null && items.get(position).getImage().length() > 0){
            Picasso.with(activity)
                    .load(items.get(position).getImage())
                    .error(R.drawable.error_image)
                    .into(holder.artistImage);
        }

    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView artistImage;
        TextView artistName, popularity, followers;


        ViewHolder(View v) {
            super(v);
            artistImage = (ImageView) v.findViewById(R.id.artist_image);
            artistName = (TextView) v.findViewById(R.id.artist_name);
            popularity = (TextView) v.findViewById(R.id.artist_popularity);
            followers = (TextView) v.findViewById(R.id.artist_followers);

        }
    }
}
