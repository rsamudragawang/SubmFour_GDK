package com.ganargatul.submfour.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ganargatul.submfour.R;
import com.ganargatul.submfour.model.MovieTvItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieTvAdapter extends RecyclerView.Adapter<MovieTvAdapter.MovieTvViewHolder> {
    ArrayList<MovieTvItems>mMovieTvItems = new ArrayList<>();
    Context mContext;
    OnItemClickListener mListener;

    public MovieTvAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public interface OnItemClickListener{
        void onItemCLick(int position);
    }
    public void SetOnItemClickListener(OnItemClickListener mListener){
        this.mListener = mListener;
    }
    public void setmMovieTvItems(ArrayList<MovieTvItems> movieTvItems) {

        mMovieTvItems.clear();
        this.mMovieTvItems.addAll(movieTvItems);
        notifyDataSetChanged();
    }

    public void addMovieTvItems(final MovieTvItems movieTvItems){
        mMovieTvItems.add(movieTvItems);
        notifyDataSetChanged();
    }
    public void cleardata(){
        mMovieTvItems.clear();
    }


    @NonNull
    @Override
    public MovieTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.movietv_items,parent,false);
        return new MovieTvViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTvViewHolder holder, int position) {
        MovieTvItems movieTvItems = mMovieTvItems.get(position);
       holder.mTitle.setText(movieTvItems.getTitle());

        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500"+movieTvItems.getPhoto()).into(holder.mPoster);
        Log.d("image",movieTvItems.getTitle());
    }

    @Override
    public int getItemCount() {
        return mMovieTvItems.size();
    }

    public class MovieTvViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        CircleImageView mPoster;
        public MovieTvViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title_items);
            mPoster = itemView.findViewById(R.id.image_items);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemCLick(position);
                        }
                    }
                }
            });

        }
    }
}
