package com.ganargatul.submfour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ganargatul.submfour.db.MovieHelper;
import com.ganargatul.submfour.db.TvHelper;
import com.ganargatul.submfour.fragment.Movie;
import com.ganargatul.submfour.fragment.MovieFav;
import com.ganargatul.submfour.fragment.TV;
import com.ganargatul.submfour.fragment.TvFav;
import com.ganargatul.submfour.model.MovieTvItems;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity  {
    public static final String EXTRA_DETAIL="extra_detail";
    int id ;
    TextView title,overview;
    ImageView poster;
    ProgressBar progressBar;
    MovieTvItems mMovieTvItems;
    MovieHelper movieHelper;
    TvHelper mTvHelper;
    MovieTvItems movieTvItems;
    Boolean act = true;
    Boolean insert = true;
    Boolean delete = true;
    FloatingActionButton fab;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fab = findViewById(R.id.floatingActionButton);
        progressBar = findViewById(R.id.progress_detail);
        title = findViewById(R.id.title_detail);
        overview = findViewById(R.id.desc_detail);
        poster = findViewById(R.id.image_detail);

        mMovieTvItems = new MovieTvItems();
        progressBar.setVisibility(View.VISIBLE);
        movieTvItems = getIntent().getParcelableExtra(EXTRA_DETAIL);
        Log.d("iddd", String.valueOf(movieTvItems.getId()));
        type = movieTvItems.getType();
        Log.d("type",type);
        movieHelper = MovieHelper.getINSTANCE(DetailActivity.this);
        mTvHelper = TvHelper.getINSTANCE(getApplicationContext());
        String name = movieTvItems.getTitle();

        if(type.equals("MOVIE")  && movieHelper.getOne(name) ){
                //delete movie
            Log.d("movie test","test");
                act = false;
                delete = true;
                fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_favorite_black_24dp));
        }else if(type.equals("MOVIE") && !movieHelper.getOne(name)){
            // savemovie
            Log.d("movie test","test");
            act = true;
            insert = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_favorite_border_black_24dp));
        }
        else if (type.equals("TV") && mTvHelper.getOne(name)){
                //delete tv
                act = false;
                delete = false;
                fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_favorite_black_24dp));

        }else if(type.equals("TV") && !mTvHelper.getOne(name)){
            //save tv
            act = true;
            insert = false;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_favorite_border_black_24dp));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabclick();
            }
        });
        ShowDetail();
    }

    private void ShowDetail() {
        progressBar.setVisibility(View.GONE);
        movieTvItems = getIntent().getParcelableExtra(EXTRA_DETAIL);
        title.setText(movieTvItems.getTitle());
        overview.setText(movieTvItems.getOverview());
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500"+ movieTvItems.getPhoto()).into(poster);

    }
    private void fabclick(){
        if (insert && act && type.equals("MOVIE")){
            mMovieTvItems.setTitle(movieTvItems.getTitle());
            mMovieTvItems.setOverview(movieTvItems.getOverview());
            mMovieTvItems.setPhoto(movieTvItems.getPhoto());
            Log.d("savemovie",overview.getText().toString());
            long result = movieHelper.insertMovie(mMovieTvItems);

            if(result > 0){
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_black_24dp));
            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();
            }
        }else if(!insert&&act && type.equals("TV") ){
            mMovieTvItems.setTitle(movieTvItems.getTitle());
            mMovieTvItems.setOverview(movieTvItems.getOverview());
            mMovieTvItems.setPhoto(movieTvItems.getPhoto());
            Log.d("savetv",overview.getText().toString());
            long result = mTvHelper.insertTv(mMovieTvItems);
            if(result > 0){
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_black_24dp));

            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();
            }
        }else if(delete && !act && type.equals("MOVIE")){
            Log.d("deletemovie",overview.getText().toString());

            long result = movieHelper.deleteMovie(movieTvItems.getTitle());
            if(result > 0 ){
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailActivity.this,MainActivity.class));
            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();

            }
        }else if(!delete && !act && type.equals("TV")){

            Log.d("deletetv",overview.getText().toString());
            long result = mTvHelper.deleteTv(movieTvItems.getTitle());
            if(result > 0 ){
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailActivity.this,MainActivity.class));
            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();

            }
        }
    }


}
