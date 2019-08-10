package com.ganargatul.submfour.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ganargatul.submfour.DetailActivity;
import com.ganargatul.submfour.R;
import com.ganargatul.submfour.adapter.MovieTvAdapter;
import com.ganargatul.submfour.model.MovieTvItems;
import com.ganargatul.submfour.viewmodel.TvViewModel;

import java.util.ArrayList;

import static com.ganargatul.submfour.DetailActivity.EXTRA_DETAIL;



/**
 * A simple {@link Fragment} subclass.
 */
public class TV extends Fragment  implements MovieTvAdapter.OnItemClickListener{

    MovieTvAdapter mMovieTvAdapter;
    TvViewModel mTvViewModel;
    ProgressBar mProgressBar;

    public TV() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tv, container, false);
        mProgressBar = v.findViewById(R.id.progress_tv);
        mProgressBar.setVisibility(View.VISIBLE);

        mMovieTvAdapter = new MovieTvAdapter(getContext());
        mMovieTvAdapter.SetOnItemClickListener(TV.this);
        mMovieTvAdapter.notifyDataSetChanged();

        mTvViewModel = ViewModelProviders.of(TV.this).get(TvViewModel.class);
        mTvViewModel.getmTvItems().observe(TV.this,getmTvItems);
        mTvViewModel.getTV();

        RecyclerView mRecyclerView = v.findViewById(R.id.tv_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(mMovieTvAdapter);
        return v;
    }
    private Observer<? super ArrayList<MovieTvItems>> getmTvItems = new Observer<ArrayList<MovieTvItems>>() {
        @Override
        public void onChanged(ArrayList<MovieTvItems> movieTvItems) {
            if(movieTvItems!=null){
                mMovieTvAdapter.setmMovieTvItems(movieTvItems);
                mProgressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onItemCLick(int position) {
        String type = "TV";
        MovieTvItems movieTv_items = new MovieTvItems();
        movieTv_items.setPhoto(mTvViewModel.mListMovieItems.get(position).getPhoto());
        movieTv_items.setTitle(mTvViewModel.mListMovieItems.get(position).getTitle());
        movieTv_items.setOverview(mTvViewModel.mListMovieItems.get(position).getOverview());
        movieTv_items.setType(type);
        Intent detail = new Intent(getContext(), DetailActivity.class);
        detail.putExtra(EXTRA_DETAIL,movieTv_items);
        startActivity(detail);
    }
}
