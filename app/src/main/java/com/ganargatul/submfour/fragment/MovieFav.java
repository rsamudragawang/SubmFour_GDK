package com.ganargatul.submfour.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ganargatul.submfour.DetailActivity;
import com.ganargatul.submfour.LoadFavCallback;
import com.ganargatul.submfour.R;
import com.ganargatul.submfour.adapter.MovieTvAdapter;
import com.ganargatul.submfour.db.MovieHelper;
import com.ganargatul.submfour.model.MovieTvItems;
import com.ganargatul.submfour.viewmodel.MovieViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ganargatul.submfour.DetailActivity.EXTRA_DETAIL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFav extends Fragment implements LoadFavCallback, MovieTvAdapter.OnItemClickListener {

    MovieTvAdapter mMovieTvAdapter;
    ProgressBar mProgressBar;
    MovieHelper mMovieHelper;
    ArrayList<MovieTvItems>mListFav = new ArrayList<>();
    public MovieFav() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_fav, container, false);
        mProgressBar = v.findViewById(R.id.progress_movie_fav);
        RecyclerView mRecyclerView = v.findViewById(R.id.movie_fav_container);

        mMovieHelper = MovieHelper.getINSTANCE(getContext());
        mMovieHelper.open();


        mMovieTvAdapter = new MovieTvAdapter(getContext());
        mMovieTvAdapter.SetOnItemClickListener(MovieFav.this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(mMovieTvAdapter);

        new LoadMovieAsync(mMovieHelper,this).execute();
        return v;
    }


    @Override
    public void preExecute() {
          mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void postExecute(ArrayList<MovieTvItems> mMovieTvItems) {
        mProgressBar.setVisibility(View.GONE);
        mMovieTvAdapter.setmMovieTvItems(mMovieTvItems);
        mListFav.addAll(mMovieTvItems);
    }

    @Override
    public void onItemCLick(int position) {
        String type = "MOVIE";
        MovieTvItems movieTv_items = new MovieTvItems();
        movieTv_items.setId(mListFav.get(position).getId());
        Log.d("idfav", String.valueOf(mListFav.get(position).getId()));
        movieTv_items.setPhoto(mListFav.get(position).getPhoto());
        movieTv_items.setTitle(mListFav.get(position).getTitle());
        movieTv_items.setOverview(mListFav.get(position).getOverview());
        movieTv_items.setType(type);
        Intent detail = new Intent(getContext(), DetailActivity.class);

        detail.putExtra(EXTRA_DETAIL,movieTv_items);
        startActivity(detail);
    }

    private class LoadMovieAsync  extends AsyncTask<Void, Void, ArrayList<MovieTvItems>> {
        WeakReference<MovieHelper>movieHelperWeakReference;
        WeakReference<LoadFavCallback>loadFavCallbackWeakReference;
        public LoadMovieAsync(MovieHelper mMovieHelper, LoadFavCallback context) {
            movieHelperWeakReference = new WeakReference<>(mMovieHelper);
            loadFavCallbackWeakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadFavCallbackWeakReference.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieTvItems> movieTvItems) {
            super.onPostExecute(movieTvItems);
            loadFavCallbackWeakReference.get().postExecute(movieTvItems);
        }

        @Override
        protected ArrayList<MovieTvItems> doInBackground(Void... voids) {
            return movieHelperWeakReference.get().getAllMovie();
        }
    }
}
