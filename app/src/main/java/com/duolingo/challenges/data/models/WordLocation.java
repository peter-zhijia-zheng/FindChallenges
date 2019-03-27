package com.duolingo.challenges.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class WordLocation implements Parcelable {
    public List<WordCoordinate> coordinates;
    public String word;

    public WordLocation(List<WordCoordinate> coordinates, String word) {
        this.coordinates = coordinates;
        this.word = word;
    }

    WordLocation(Parcel source) {
        source.readList(coordinates, WordCoordinate.class.getClassLoader());
        word = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(coordinates);
        dest.writeString(word);
    }

    public static final Parcelable.Creator<WordLocation> CREATOR = new Parcelable.Creator<WordLocation>() {
        @Override
        public WordLocation createFromParcel(Parcel source) {
            return new WordLocation(source);
        }

        @Override
        public WordLocation[] newArray(int size) {
            return new WordLocation[size];
        }
    };
}