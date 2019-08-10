package com.ganargatul.submfour.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ganargatul.submfour.R;
import com.ganargatul.submfour.model.MovieTvItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieViewModel extends AndroidViewModel {

    RequestQueue mRequestQueue;
    public MutableLiveData<ArrayList<MovieTvItems>> mMovieTvItems = new MutableLiveData<>();
    public ArrayList<MovieTvItems>mListMovieItems = new ArrayList<>();
    String url;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRequestQueue = Volley.newRequestQueue(application);
        url=application.getResources().getString(R.string.api_movie);
    }

    public void getMovie(){

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    int length = jsonArray.length();
                    for(int i = 0;i<length;i++){
                        JSONObject result = jsonArray.getJSONObject(i);
                        String title = result.getString("title");
                        String photo = result.getString("poster_path");
                        String overview = result.getString("overview");
                        MovieTvItems movieTvItems = new MovieTvItems();
                        movieTvItems.setTitle(title);
                        movieTvItems.setPhoto(photo);
                        movieTvItems.setOverview(overview);
                        Log.d("title",photo);
                        mListMovieItems.add(movieTvItems);
                    }
                    mMovieTvItems.postValue(mListMovieItems);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(mRequest);
    }

    public LiveData<ArrayList<MovieTvItems>> getmMovieTvItems() {
        return mMovieTvItems;
    }
}
