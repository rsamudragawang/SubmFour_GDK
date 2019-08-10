package com.ganargatul.submfour.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieTvItems implements Parcelable {
    String title,photo,overview,type;
    int id;
    public MovieTvItems() {

    }

    protected MovieTvItems(Parcel in) {
        title = in.readString();
        photo = in.readString();
        overview = in.readString();
        type = in.readString();
        id = in.readInt();
    }

    public static final Creator<MovieTvItems> CREATOR = new Creator<MovieTvItems>() {
        @Override
        public MovieTvItems createFromParcel(Parcel in) {
            return new MovieTvItems(in);
        }

        @Override
        public MovieTvItems[] newArray(int size) {
            return new MovieTvItems[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public String getPhoto() {
        return photo;
    }

    public String getOverview() {
        return overview;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(photo);
        parcel.writeString(overview);
        parcel.writeString(type);
        parcel.writeInt(id);
    }
}
