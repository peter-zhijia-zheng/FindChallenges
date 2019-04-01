package com.duolingo.challenges.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class WordCoordinate implements Parcelable {
    public static final Parcelable.Creator<WordCoordinate> CREATOR = new Parcelable.Creator<WordCoordinate>() {
        @Override
        public WordCoordinate createFromParcel(Parcel source) {
            return new WordCoordinate(source);
        }

        @Override
        public WordCoordinate[] newArray(int size) {
            return new WordCoordinate[size];
        }
    };
    public final int x, y;

    public WordCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    WordCoordinate(Parcel source) {
        this.x = source.readInt();
        this.y = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
    }
}