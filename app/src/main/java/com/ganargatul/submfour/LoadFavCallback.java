package com.ganargatul.submfour;

import com.ganargatul.submfour.model.MovieTvItems;

import java.util.ArrayList;

public interface LoadFavCallback {
    void preExecute();
    void postExecute(ArrayList<MovieTvItems>mMovieTvItems);
}
